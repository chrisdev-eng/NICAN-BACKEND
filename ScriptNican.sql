
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

select * from admin;

insert into admin (nome, login, senha, criadoEm, atualizadoEm)
values 
('Christian','chris@gmail.com', '12345679', now(), now()),
('Henrique','henrique@gmail.com', '97654321', now(), now());

delete from admin where idAdmin = 2;


-- _______________________________________________
--  armazenar usuários comuns, poder solicitar itens, reportar estado de ferramentas
--  e justificar faltas
-- _______________________________________________

create table usuario(
	idUsuario serial primary key,
	nome varchar(100) not null,
	login varchar(100) not null,
	senha varchar(100) not null,
	idAdmin_fk INTEGER not null,
	criadoEm timestamp,
	atualizadoEm timestamp,
	foreign key (idAdmin_fk) references admin(idAdmin)
);

select * from usuario;

insert into usuario(nome, login, senha, idAdmin_fk, criadoEm, atualizadoEm)
values
('Daniel','daniel@gmail.com','456123',1, now(), now()),
('Lucas','lucas@gmail.com','9090876',1, now(), now()),
('Peter','peter@gmail.com','1152345',3, now(), now()),
('Bella','bella@gmail.com','4561237',3, now(), now());

-- _______________________________________________
--  cada item/ferramenta existente no estoque, guarda qtd total e disponível
--  e qual admin é responsável por aquele item
-- _______________________________________________

create table almoxarifado(
	idItem serial primary key,
	nome varchar(100) not null,
	categoria varchar(100) not null,
	quantidadeTotal integer,
	quantidadeDisp integer,
	idAdmin_fk integer not null,
	criadoEm timestamp,
	atualizadoEm timestamp,
	foreign key (idAdmin_fk) references admin(idAdmin)
	
);

select * from almoxarifado;

insert into almoxarifado(nome, categoria, quantidadeTotal, quantidadeDisp, idAdmin_fk, criadoEm, AtualizadoEm)
values 
('Barraca de Camping', 'Equipamento',15,14, 1, now(), now() ),
('Bússola', 'Navegação',10, 9, 1, now(), now() ),
('Canivete','Ferramenta',5, 0, 1, now(), now() ),
('Lanterna','Iluminação', 20, 15, 1, now(), now() ),
('Corda','Equipamento', 7, 6, 3, now(), now() ),
('Pá','Ferramenta', 15, 13, 3, now(), now() );

-- _______________________________________________
--  pedido formal que o usuário faz para pegar o item emprestado
--  pendente até aprovação ou negativa do admin operacionalmente
-- _______________________________________________

create table requerimento(
	idRequerimento serial primary key not null,
	idUsuario_fk integer not null,
	idItem_fk integer not null,
	qtdSolicitado integer not null,
	status varchar(10),
	idAdmin_fk integer,
	dataSolicitacao date,
	dataAprovacao date, -- tanto para aprovação ou negação
	criadoEm timestamp,
	atualizadoEm timestamp,
	foreign key (idUsuario_fk) references usuario(idUsuario),
	foreign key (idItem_fk) references almoxarifado(idItem),
	foreign key (idAdmin_fk) references admin(idAdmin)
);

alter table requerimento alter column idAdmin_fk drop not null;

insert into requerimento(idUsuario_fk, idItem_fk,qtdSolicitado, status, idAdmin_fk, dataSolicitacao, dataAprovacao, criadoEm, atualizadoEm)
values -- ano/mes/dia
(1, 4, 1, 'pendente', null, '2025-04-03', null, now(), now() ), -- daniel pendente
(2, 6, 2, 'pendente', null, '2025-04-04', null, now(), now() ), -- lucas  pendente
(2, 1, 1, 'aprovado', 3, '2025-04-02', '2025-04-02', now(), now() ), -- lucas aprovado
(3, 5, 4, 'aprovado', 1, '2025-04-03', '2025-04-03', now(), now() ), -- peter aprovado
(3, 1, 3, 'aprovado', 1, '2025-04-03', '2025-04-03', now(), now() ), -- peter aprovado
(1, 3, 3, 'reprovado', 3, '2025-04-04', '2025-04-04', now(), now() ); -- daniel vai ser negado

select * from requerimento;

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
    estadoItem varchar(100) not null,          -- estado ao pegar: 'bom', 'danificado', etc.
    obsEstado text,                   -- observação livre sobre o estado
    criadoEm timestamp,
    atualizadoEm timestamp,
    foreign key (idRequerimento_fk) references requerimento(idRequerimento),
    foreign key (idUsuario_fk) references usuario(idUsuario),
    foreign key (idItem_fk) references almoxarifado(idItem)
);

insert into emprestar(idRequerimento_fk, idUsuario_fk, idItem_fk, qtdPega, dataPegou, devPrevista, dataDev, estadoItem, obsEstado, criadoEm, atualizadoEm)
values
(3, 2, 1, 1, '2025-04-03', '2025-04-10', '2025-04-09', 'bom', 'devolvida sem danos', now(), now()),
(4, 3, 5, 4, '2025-04-04', '2025-04-11', null, 'bom', null, now(), now()),
(5, 3, 1, 3, '2025-04-04', '2025-04-11', null, 'danificado', 'apresentava rasgado em uma das laterais', now(), now());

select * from emprestar;

-- _______________________________________________
--  registra quando admin adc ou remove itens do almoxarifado fisicamente
--  ex: chegaram 10 martelos novos e 2 foram descartados
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

-- movimentações de estoque
insert into movimentoEstoque(idItem_fk, idAdmin_fk, tipoAcao, qtd, observacao, criadoEm)
values

(4, 1, 'entrada', 10, 'compra de novas lanternas para reposição', now()), -- lanternas nuevas
(3, 1, 'saida', 2, 'canivetes danificados descartados', now()), -- 2 canivetes pro beleleu
(5, 3, 'entrada', 3, 'reposição de cordas de rappel', now()), -- corditas nuevas
(2, 3, 'saida', 1, 'bússola com defeito descartada', now()); -- bússolas pro beleleu

select * from movimentoEstoque;


-- _________________________________________________
-- ============     SELECTS E JOINS =============
-- _________________________________________________

-- nome do usuario e status do requerimento
select usuario.nome, requerimento.status, requerimento.idrequerimento 
from usuario
join requerimento on usuario.idusuario = requerimento.idusuario_fk;

-- nome do usuario, item emprestado e a data que pegou
select usuario.nome, almoxarifado. nome, emprestar.dataPegou
from emprestar
join usuario on emprestar.idusuario_fk = usuario.idUsuario
join almoxarifado on emprestar.iditem_fk = almoxarifado.idItem;

-- requerimento com nome de usuario e o item e quem aprovou o trem
select usuario.nome, almoxarifado.nome, requerimento.status, admin.nome, requerimento.dataAprovacao
from requerimento 
join usuario on requerimento.idUsuario_fk = usuario.idUsuario 
join almoxarifado on requerimento.idItem_fk = almoxarifado.idItem
left join admin on requerimento.idAdmin_fk = admin.idAdmin;

-- movimentacoes com o nome do item e admin responsavel
select almoxarifado.nome, admin.nome, movimentoEstoque.tipoAcao, movimentoEstoque.qtd, movimentoEstoque.observacao
from movimentoestoque
join almoxarifado on movimentoestoque.iditem_fk = almoxarifado.idItem 
join admin on movimentoEstoque.idAdmin_fk = admin.idAdmin;

-- ________________________________
--     UPDATES
-- _________________________________

-- aprovando o requerimento(id) 1 do daniels
update requerimento
set status = 'aprovado', idAdmin_fk = 1, dataAprovacao = '2025-04-07', atualizadoEm = now()
where idrequerimento = 1;

-- devolução de um item que foi emprestado
update emprestar
set dataDev = '2025-04-07', atualizadoEm = now()
where idEmprestimo =2;

-- atualizar qtd disponivel no estoque
update almoxarifado
set quantidadeDisp = 10, atualizadoEm = now()
where idItem = 4;


-- ________________________________
--     TRIGGERS
-- _________________________________

-- le funcione para quando houver emprestimo de item almoxarifado
create or replace function diminuir_estoque_almoxarifado()
returns trigger as $$
begin
    if new.qtdPega > (select quantidadeDisp from almoxarifado where idItem = new.idItem_fk) then
        raise exception 'Estoque insuficiente para o item %', new.idItem_fk;
    end if;

    update almoxarifado
    set quantidadeDisp = quantidadeDisp - new.qtdPega
    where idItem = new.idItem_fk;

    return new;
end;
$$ language plpgsql;

--_______________________________________________
-- função pra quando houver devolução do emprestimo
-- _______________________________________________
create or replace function retorno_estoque_almoxarifado()
returns trigger as $$
begin 
	if new.dataDev is not null and old.dataDev is null then
		update almoxarifado
		set quantidadeDisp = quantidadeDisp + new.qtdPega
		where idItem = new.idItem_fk;
		end if;
		
return new;
end;
$$ language plpgsql;

--_______________________________________________
-- função pra aprovação/negativa requerimento
-- _______________________________________________

create or replace function status_requerimento()
returns trigger as $$
begin
	if new.status ='aprovado' and old.status ='pendente' then
	insert into emprestar(idRequerimento_fk, idUsuario_fk, idItem_fk,qtdPega, dataPegou, devPrevista, estadoItem)
	values (new.idRequerimento, new.idUsuario_fk, new.idItem_fk, new.qtdSolicitado, now(), now() + interval '7 days', 'a verificar');
	end if;

return new;
end;
$$ language plpgsql;



-- _______________________________________________
-- triggers
-- _______________________________________________

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






