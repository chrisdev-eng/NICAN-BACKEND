package com.faculdade.nican.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *  ~ Entidade de Empréstimo ~
 *
 *  Criada automaticamente via trigger no banco quando um requerimento é aprovado.
 *  Registra a saída do item, prazo de devolução e quando foi devolvido.
 */
@Entity
@Table(name = "emprestar")
public class Emprestimo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idEmprestimo")
  private Integer idEmprestimo;

  @ManyToOne
  @JoinColumn(name = "idRequerimento_fk", nullable = false)
  private Requerimento requerimento;

  @ManyToOne
  @JoinColumn(name = "idUsuario_fk", nullable = false)
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "idItem_fk", nullable = false)
  private Item item;

  @Column(name = "qtdPega", nullable = false)
  private Integer qtdPega;

  @Column(name = "dataPegou", nullable = false)
  private LocalDate dataPegou;

  @Column(name = "devPrevista", nullable = false)
  private LocalDate devPrevista;

  @Column(name = "dataDev")
  private LocalDate dataDev;

  @Column(name = "estadoItem", nullable = false)
  private String estadoItem;

  @Column(name = "obsEstado")
  private String obsEstado;

  @Column(name = "criadoEm")
  private LocalDateTime criadoEm;

  @Column(name = "atualizadoEm")
  private LocalDateTime atualizadoEm;



  public Emprestimo() {}



  // GETTERS
  public Integer      getIdEmprestimo()  { return idEmprestimo; }
  public Requerimento getRequerimento()  { return requerimento; }
  public Usuario      getUsuario()       { return usuario; }
  public Item         getItem()          { return item; }
  public Integer      getQtdPega()       { return qtdPega; }
  public LocalDate    getDataPegou()     { return dataPegou; }
  public LocalDate    getDevPrevista()   { return devPrevista; }
  public LocalDate    getDataDev()       { return dataDev; }
  public String       getEstadoItem()    { return estadoItem; }
  public String       getObsEstado()     { return obsEstado; }
  public LocalDateTime getCriadoEm()    { return criadoEm; }
  public LocalDateTime getAtualizadoEm(){ return atualizadoEm; }

  // SETTERS
  public void setDataDev(LocalDate dataDev)         { this.dataDev = dataDev; }
  public void setEstadoItem(String estadoItem)      { this.estadoItem = estadoItem; }
  public void setObsEstado(String obsEstado)        { this.obsEstado = obsEstado; }
  public void setAtualizadoEm(LocalDateTime dt)     { this.atualizadoEm = dt; }

  /** Verifica se o empréstimo está em atraso (não devolvido e passou da data prevista) */
  public boolean estaAtrasado() {
    return dataDev == null && LocalDate.now().isAfter(devPrevista);
  }

  /** Verifica se já foi devolvido */
  public boolean foiDevolvido() {
    return dataDev != null;
  }
}
