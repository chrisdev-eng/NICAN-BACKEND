package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.login.classes.Admin;
import com.faculdade.projeto.login.classes.Usuario;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 *  ~ Entidade de Requerimento do Sistema ~
 *
 *  CORREÇÕES APLICADAS:
 *    - @JoinColumn(name = "idUsuario") — corrigido para bater com a coluna do banco
 *    - @JoinColumn(name = "idItem")    — corrigido para bater com a coluna do banco
 *    - @Column(name = "quantidadeSolicitada") — corrigido (banco usava qtdSolicitado, agora alinhado)
 *    - @JoinColumn(name = "idAdmin")  — corrigido para bater com a coluna do banco
 *    - adminAvaliador era do tipo "Usuario" — o correto é "Admin" (quem aprova é um admin)
 *    - Removido campo atualizadoEm (nao existe no SQL de requerimento)
 */
@Entity
@Table(name = "requerimento")
public class Requerimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idRequerimento;

  // CORRECAO: nome da coluna alinhado com o SQL corrigido
  @ManyToOne
  @JoinColumn(name = "idUsuario", nullable = false)
  private Usuario usuario;

  // CORRECAO: nome da coluna alinhado com o SQL corrigido
  @ManyToOne
  @JoinColumn(name = "idItem", nullable = false)
  private Item item;

  // CORRECAO: nome da coluna alinhado com o SQL corrigido
  @Column(name = "quantidadeSolicitada")
  private Integer quantidadeSolicitada;

  private String status;  // pendente | aprovado | recusado

  // CORRECAO: era "Usuario adminAvaliador" — deve ser "Admin" pois só admins avaliam
  // CORRECAO: nome da coluna alinhado com o SQL corrigido
  @ManyToOne
  @JoinColumn(name = "idAdmin")
  private Admin adminAvaliador;

  private LocalDate dataAprovacao;
  private LocalDate dataSolicitacao;
  private LocalDateTime criadoEm;



  public Requerimento() {}

  public Requerimento(Usuario usuario, Item item, Integer qtd) {
    this.usuario = usuario;
    this.item = item;
    this.quantidadeSolicitada = qtd;
    this.status = "pendente";
    this.dataSolicitacao = LocalDate.now();
    this.criadoEm = LocalDateTime.now();
  }



  // GETTERS
  public Integer  getIdRequerimento()       { return idRequerimento; }
  public Usuario  getUsuario()              { return usuario; }
  public Item     getItem()                 { return item; }
  public Integer  getQuantidadeSolicitada() { return quantidadeSolicitada; }
  public String   getStatus()              { return status; }
  public Admin    getAdminAvaliador()      { return adminAvaliador; }
  public LocalDate getDataAprovacao()      { return dataAprovacao; }
  public LocalDate getDataSolicitacao()    { return dataSolicitacao; }
  public LocalDateTime getCriadoEm()       { return criadoEm; }



  // SETTERS
  public void setUsuario(Usuario usuario)              { this.usuario = usuario; }
  public void setItem(Item item)                       { this.item = item; }
  public void setQuantidadeSolicitada(Integer qtd)     { this.quantidadeSolicitada = qtd; }
  public void setStatus(String status)                 { this.status = status; }
  public void setAdminAvaliador(Admin adminAvaliador)  { this.adminAvaliador = adminAvaliador; }



  // MÉTODO DE NEGÓCIO: aprovacao por admin
  // REGRA DE NEGÓCIO: somente um Admin pode aprovar um requerimento
  public void aprovar(Admin admin) {
    this.status = "aprovado";
    this.adminAvaliador = admin;
    this.dataAprovacao = LocalDate.now();
  }

  // REGRA DE NEGÓCIO: recusa por admin
  public void recusar(Admin admin) {
    this.status = "recusado";
    this.adminAvaliador = admin;
    this.dataAprovacao = LocalDate.now();
  }
}
