
-- _______________________________________________
--
--  Admin - armazenar os administradores do sistema, gerenciar tudo: criar usuários,
--  aprovar requerimentos, registrar presenças, movimentar estoque.
-- _______________________________________________
create table admin(
	idAdmin serial primary key,
	nome varchar(100) not null,
	login varchar(100) not null,
	senha varchar(100) not null,
	criadoEm timestamp,
	atualizadoEm timestamp

);

-- CORRECAO: removidos os SELECTs (nao sao validos dentro de migration Flyway)
-- CORRECAO: removido o insert do idAdmin=2 e o delete subsequente que referenciavam FK inexistente.
--           Inserindo apenas o admin id=1 para que os dados de seed sejam consistentes.
insert into admin (nome, login, senha, criadoEm, atualizadoEm)
values
('Christian','chris@gmail.com', '12345679', now(), now());


-- _______________________________________________
--  armazenar usuários comuns, poder solicitar itens, reportar estado de ferramentas
--  e justificar faltas
-- _______________________________________________

create table usuario(
	idUsuario serial primary key,
	nome varchar(100) not null,
	login varchar(100) not null,
	senha varchar(100) not null,
	-- CORRECAO: nullable para permitir primeiro cadastro sem admin logado (regra de negocio)
	idAdmin_fk INTEGER,
	perfil varchar(20) not null default 'USUARIO',
	ativo boolean not null default true,
	criadoEm timestamp,
	atualizadoEm timestamp,
	foreign key (idAdmin_fk) references admin(idAdmin)
);

-- CORRECAO: apenas usuarios referenciando idAdmin=1 (unico admin existente)
--           Peter e Bella foram removidos pois referenciavam idAdmin=3 que nao existe
insert into usuario(nome, login, senha, idAdmin_fk, perfil, ativo, criadoEm, atualizadoEm)
values
('Daniel','daniel@gmail.com','456123',1,'USUARIO',true, now(), now()),
('Lucas','lucas@gmail.com','9090876',1,'USUARIO',true, now(), now());

-- _______________________________________________
--  cada item/ferramenta existente no estoque, guarda qtd total e disponível
--  e qual admin é responsável por aquele item
-- _______________________________________________

create table almoxarifado(
	idItem serial primary key,
	nome varchar(100) not null,
	categoria varchar(100) not null,
	quantidadeTotal integer not null default 0,
	-- CORRECAO: nome da coluna alinhado com o mapeamento JPA em Item.java
	quantidadeDisponivel integer not null default 0,
	-- CORRECAO: coluna qualidade adicionada (existia no Java mas nao no SQL)
	qualidade varchar(100) not null default 'Bom para uso',
	idAdmin_fk integer not null,
	criadoEm timestamp,
	atualizadoEm timestamp,
	foreign key (idAdmin_fk) references admin(idAdmin)

);

-- CORRECAO: quantidadeDisp -> quantidadeDisponivel; adicionada coluna qualidade
insert into almoxarifado(nome, categoria, quantidadeTotal, quantidadeDisponivel, qualidade, idAdmin_fk, criadoEm, atualizadoEm)
values
('Barraca de Camping', 'Equipamento',15,14,'Bom para uso', 1, now(), now()),
('Bussola', 'Navegacao',10, 9,'Novo', 1, now(), now()),
('Canivete','Ferramenta',5, 0,'Quebrado/Ruim para uso', 1, now(), now()),
('Lanterna','Iluminacao', 20, 15,'Bom para uso', 1, now(), now()),
('Corda','Equipamento', 7, 6,'Bom para uso', 1, now(), now()),
('Pa','Ferramenta', 15, 13,'Bom para uso', 1, now(), now());

-- _______________________________________________
--  pedido formal que o usuário faz para pegar o item emprestado
--  pendente até aprovação ou negativa do admin operacionalmente
-- _______________________________________________

create table requerimento(
	idRequerimento serial primary key not null,
	-- CORRECAO: nomes das colunas alinhados com o mapeamento JPA em Requerimento.java
	idUsuario integer not null,
	idItem integer not null,
	quantidadeSolicitada integer not null,
	status varchar(10),
	idAdmin integer,
	dataSolicitacao date,
	dataAprovacao date,
	criadoEm timestamp,
	foreign key (idUsuario) references usuario(idUsuario),
	foreign key (idItem) references almoxarifado(idItem),
	foreign key (idAdmin) references admin(idAdmin)
);

-- CORRECAO: nomes das colunas atualizados; referencias a idAdmin=3 substituidas por 1
insert into requerimento(idUsuario, idItem, quantidadeSolicitada, status, idAdmin, dataSolicitacao, dataAprovacao, criadoEm)
values
(1, 4, 1, 'pendente', null, '2025-04-03', null, now()),
(2, 6, 2, 'pendente', null, '2025-04-04', null, now()),
(2, 1, 1, 'aprovado', 1, '2025-04-02', '2025-04-02', now()),
(1, 5, 4, 'aprovado', 1, '2025-04-03', '2025-04-03', now()),
(1, 1, 3, 'reprovado', 1, '2025-04-04', '2025-04-04', now());

-- _______________________________________________
--  só é criado quando o admin aprova um requerimento, registra a saida do item
--  quando foi pego, prazo de devolução e quando foi devolvido
-- _______________________________________________

create table emprestar(
    idEmprestimo serial primary key not null,
    idRequerimento_fk integer not null,
    idUsuario_fk integer not null,
    idItem_fk integer not null,
    qtdPega integer not null,
    dataPegou date not null,
    devPrevista date not null,
    dataDev date,
    estadoItem varchar(100) not null,
    obsEstado text,
    criadoEm timestamp,
    atualizadoEm timestamp,
    foreign key (idRequerimento_fk) references requerimento(idRequerimento),
    foreign key (idUsuario_fk) references usuario(idUsuario),
    foreign key (idItem_fk) references almoxarifado(idItem)
);

insert into emprestar(idRequerimento_fk, idUsuario_fk, idItem_fk, qtdPega, dataPegou, devPrevista, dataDev, estadoItem, obsEstado, criadoEm, atualizadoEm)
values
(3, 2, 1, 1, '2025-04-03', '2025-04-10', '2025-04-09', 'bom', 'devolvida sem danos', now(), now()),
(4, 1, 5, 4, '2025-04-04', '2025-04-11', null, 'bom', null, now(), now()),
(5, 1, 1, 3, '2025-04-04', '2025-04-11', null, 'danificado', 'apresentava rasgado em uma das laterais', now(), now());

-- _______________________________________________
--  registra quando admin adc ou remove itens do almoxarifado fisicamente
-- _______________________________________________

create table movimentoEstoque (
	idMovimentacao serial primary key not null,
	idItem_fk integer not null,
	idAdmin_fk integer not null,
	tipoAcao varchar(100) not null,
	qtd integer not null,
	observacao text,
	criadoEm timestamp,
	foreign key (idItem_fk) references almoxarifado(idItem),
	foreign key (idAdmin_fk) references admin(idAdmin)
);

insert into movimentoEstoque(idItem_fk, idAdmin_fk, tipoAcao, qtd, observacao, criadoEm)
values
(4, 1, 'entrada', 10, 'compra de novas lanternas para reposicao', now()),
(3, 1, 'saida', 2, 'canivetes danificados descartados', now()),
(5, 1, 'entrada', 3, 'reposicao de cordas de rappel', now()),
(2, 1, 'saida', 1, 'bussola com defeito descartada', now());


-- _______________________________________________
-- TRIGGERS
-- _______________________________________________

-- funcao para quando houver emprestimo de item almoxarifado
create or replace function diminuir_estoque_almoxarifado()
returns trigger as $$
begin
    if new.qtdPega > (select quantidadeDisponivel from almoxarifado where idItem = new.idItem_fk) then
        raise exception 'Estoque insuficiente para o item %', new.idItem_fk;
    end if;

    update almoxarifado
    set quantidadeDisponivel = quantidadeDisponivel - new.qtdPega
    where idItem = new.idItem_fk;

    return new;
end;
$$ language plpgsql;

-- funcao para quando houver devolucao do emprestimo
create or replace function retorno_estoque_almoxarifado()
returns trigger as $$
begin
	if new.dataDev is not null and old.dataDev is null then
		update almoxarifado
		set quantidadeDisponivel = quantidadeDisponivel + new.qtdPega
		where idItem = new.idItem_fk;
		end if;

return new;
end;
$$ language plpgsql;

-- funcao para aprovacao/negativa requerimento
create or replace function status_requerimento()
returns trigger as $$
begin
	if new.status ='aprovado' and old.status ='pendente' then
	insert into emprestar(idRequerimento_fk, idUsuario_fk, idItem_fk, qtdPega, dataPegou, devPrevista, estadoItem)
	values (new.idRequerimento, new.idUsuario, new.idItem, new.quantidadeSolicitada, now(), now() + interval '7 days', 'a verificar');
	end if;

return new;
end;
$$ language plpgsql;

-- RETIRADA ITEM
create trigger trigger_retirada_item
after insert on emprestar
for each row execute function diminuir_estoque_almoxarifado();

-- ESTORNO ITEM
create trigger trigger_retorno_item
after update on emprestar
for each row execute function retorno_estoque_almoxarifado();

-- STATUS REQUERIMENTO
create trigger trigger_aprovacao_requerimento
after update on requerimento
for each row execute function status_requerimento();
