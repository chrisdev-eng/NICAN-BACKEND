-- _______________________________________________
--  Admin
-- _______________________________________________
create table admin(
    idAdmin serial primary key,
    nome varchar(100) not null,
    login varchar(100) not null,
    senha varchar(100) not null,
    criadoEm timestamp,
    atualizadoEm timestamp
);

insert into admin (nome, login, senha, criadoEm, atualizadoEm)
values
('ADMIN MESTRE','admin@nican.com', 'Admin123', now(), now());


-- _______________________________________________
--  Usuario
-- _______________________________________________
create table usuario(
    idUsuario serial primary key,
    nome varchar(100) not null,
    login varchar(100) not null,
    senha varchar(100) not null,
    -- CORRECAO: nullable para permitir primeiro cadastro sem admin logado
    idAdmin_fk INTEGER,
    -- CORRECAO: colunas perfil e ativo adicionadas (existiam no Java mas nao no SQL original do Flyway)
    perfil varchar(20) not null default 'USUARIO',
    ativo boolean not null default true,
    criadoEm timestamp,
    atualizadoEm timestamp,
    foreign key (idAdmin_fk) references admin(idAdmin)
);

insert into usuario(nome, login, senha, idAdmin_fk, perfil, ativo, criadoEm, atualizadoEm)
values
('Daniel','daniel@gmail.com','456123',1,'USUARIO',true, now(), now()),
('Lucas','lucas@gmail.com','9090876',1,'USUARIO',true, now(), now());


-- _______________________________________________
--  Almoxarifado
-- _______________________________________________
create table almoxarifado(
    idItem serial primary key,
    nome varchar(100) not null,
    categoria varchar(100) not null,
    quantidadeTotal integer not null default 0,
    -- CORRECAO: era quantidadeDisp no ScriptNican.sql original; corrigido para quantidadeDisponivel
    --           para bater com o @Column(name="quantidadeDisponivel") em Item.java
    quantidadeDisponivel integer not null default 0,
    -- CORRECAO: coluna qualidade adicionada (existia no Java mas nao no SQL original)
    qualidade varchar(100) not null default 'Bom para uso',
    idAdmin_fk integer not null,
    criadoEm timestamp,
    atualizadoEm timestamp,
    foreign key (idAdmin_fk) references admin(idAdmin)
);

insert into almoxarifado(nome, categoria, quantidadeTotal, quantidadeDisponivel, qualidade, idAdmin_fk, criadoEm, atualizadoEm)
values
('Barraca de Camping', 'Barracas, Lonas, Sacos de Dormir',15,14,'Bom para uso', 1, now(), now()),
('Bussola', 'Ferramentas de Sapa',10, 9,'Novo', 1, now(), now()),
('Canivete','Ferramentas de Corte',5, 0,'Quebrado/Ruim para uso', 1, now(), now()),
('Lanterna','Materiais de Ramo/Secao/Alcateia', 20, 15,'Bom para uso', 1, now(), now()),
('Corda','Barracas, Lonas, Sacos de Dormir', 7, 6,'Bom para uso', 1, now(), now()),
('Pa','Ferramentas de Sapa', 15, 13,'Bom para uso', 1, now(), now());


-- _______________________________________________
--  Requerimento
-- _______________________________________________
create table requerimento(
    idRequerimento serial primary key not null,
    -- CORRECAO: era idUsuario_fk no ScriptNican.sql original; corrigido para idUsuario
    --           para bater com @JoinColumn(name="idUsuario") em Requerimento.java
    idUsuario integer not null,
    -- CORRECAO: era idItem_fk; corrigido para idItem para bater com @JoinColumn(name="idItem")
    idItem integer not null,
    -- CORRECAO: era qtdSolicitado; corrigido para quantidadeSolicitada para bater com @Column em Requerimento.java
    quantidadeSolicitada integer not null,
    status varchar(10),
    -- CORRECAO: era idAdmin_fk; corrigido para idAdmin para bater com @JoinColumn(name="idAdmin")
    idAdmin integer,
    dataSolicitacao date,
    dataAprovacao date,
    criadoEm timestamp,
    -- CORRECAO: atualizadoEm removido pois nao existe no mapeamento JPA de Requerimento.java
    foreign key (idUsuario) references usuario(idUsuario),
    foreign key (idItem) references almoxarifado(idItem),
    foreign key (idAdmin) references admin(idAdmin)
);

insert into requerimento(idUsuario, idItem, quantidadeSolicitada, status, idAdmin, dataSolicitacao, dataAprovacao, criadoEm)
values
(1, 4, 1, 'pendente', null, '2025-04-03', null, now()),
(2, 6, 2, 'pendente', null, '2025-04-04', null, now()),
(2, 1, 1, 'aprovado', 1, '2025-04-02', '2025-04-02', now()),
(1, 5, 4, 'aprovado', 1, '2025-04-03', '2025-04-03', now()),
(1, 1, 3, 'reprovado', 1, '2025-04-04', '2025-04-04', now());


-- _______________________________________________
--  Emprestar
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
--  MovimentoEstoque
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

-- CORRECAO: trigger usa quantidadeDisponivel (era quantidadeDisp no ScriptNican.sql original)
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

-- CORRECAO: trigger usa quantidadeDisponivel (era quantidadeDisp)
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

-- CORRECAO: trigger usa os nomes de coluna corretos (idUsuario, idItem, quantidadeSolicitada)
create or replace function status_requerimento()
returns trigger as $$
begin
    if new.status = 'aprovado' and old.status = 'pendente' then
        insert into emprestar(idRequerimento_fk, idUsuario_fk, idItem_fk, qtdPega, dataPegou, devPrevista, estadoItem)
        values (new.idRequerimento, new.idUsuario, new.idItem, new.quantidadeSolicitada, now(), now() + interval '7 days', 'a verificar');
    end if;

    return new;
end;
$$ language plpgsql;


create trigger trigger_retirada_item
after insert on emprestar
for each row execute function diminuir_estoque_almoxarifado();

create trigger trigger_retorno_item
after update on emprestar
for each row execute function retorno_estoque_almoxarifado();

create trigger trigger_aprovacao_requerimento
after update on requerimento
for each row execute function status_requerimento();
