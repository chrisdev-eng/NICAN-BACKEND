package com.faculdade.projeto.login.classes;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/*
 *  Classe Admin do Sistema.
 *
 *  CORREÇÕES APLICADAS:
 *    - @OneToMany(mappedBy = "adminResponsavel") — mantido pois o campo se chama
 *      "adminResponsavel" em Usuario e Item.
 *    - Sem outras alteracoes estruturais.
 */
@Entity
@Table(name = "admin")
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idAdmin")
  private Integer id;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "login", nullable = false, length = 100, unique = true)
  private String login;

  @Column(name = "senha", nullable = false, length = 255)
  private String senha;

  @CreationTimestamp
  @Column(name = "criadoEm", updatable = false)
  private LocalDateTime criadoEm;

  @UpdateTimestamp
  @Column(name = "atualizadoEm")
  private LocalDateTime atualizadoEm;

  // @OneToMany — um admin é responsável por varios usuarios
  // mappedBy aponta para o campo "adminResponsavel" da classe Usuario
  @OneToMany(mappedBy = "adminResponsavel", fetch = FetchType.LAZY)
  private List<Usuario> usuarios;

  // @OneToMany — um admin é responsável por varios itens do almoxarife
  @OneToMany(mappedBy = "adminResponsavel", fetch = FetchType.LAZY)
  private List<com.faculdade.projeto.almoxarife.classes.Item> itens;



  public Admin() {}

  public Admin(String nome, String login, String senha) {
    this.nome = nome;
    this.login = login;
    this.senha = senha;
  }



  // GETTERS
  public Integer getId()                     { return id; }
  public String  getNome()                   { return nome; }
  public String  getLogin()                  { return login; }
  public String  getSenha()                  { return senha; }
  public LocalDateTime getCriadoEm()         { return criadoEm; }
  public LocalDateTime getAtualizadoEm()     { return atualizadoEm; }
  public List<Usuario> getUsuarios()         { return usuarios; }
  public List<com.faculdade.projeto.almoxarife.classes.Item> getItens() { return itens; }



  // SETTERS
  public void setId(Integer id)                          { this.id = id; }
  public void setNome(String nome)                       { this.nome = nome.trim(); }
  public void setLogin(String login)                     { this.login = login.trim().toLowerCase(); }
  public void setSenha(String senha)                     { this.senha = senha; }
  public void setAtualizadoEm(LocalDateTime dt)          { this.atualizadoEm = dt; }
}
