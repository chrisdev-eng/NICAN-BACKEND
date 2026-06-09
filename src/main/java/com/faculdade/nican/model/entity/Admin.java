package com.faculdade.nican.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/*
 *  Classe Admin do Sistema.
 *
 *  CORREÇÕES APLICADAS:
 *    - Construtor agora chama trim().toLowerCase() no login (igual ao setter),
 *      evitando inconsistência entre cadastro e login quando o e-mail tem maiúsculas.
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

  @OneToMany(mappedBy = "adminResponsavel", fetch = FetchType.LAZY)
  private List<Usuario> usuarios;

  @OneToMany(mappedBy = "adminResponsavel", fetch = FetchType.LAZY)
  private List<Item> itens;



  public Admin() {}

  public Admin(String nome, String login, String senha) {
    this.nome  = nome.trim();
    // CORREÇÃO: normaliza o login no construtor, igual ao setter
    this.login = login.trim().toLowerCase();
    this.senha = senha;
  }



  // GETTERS
  public Integer getId()                 { return id; }
  public String  getNome()               { return nome; }
  public String  getLogin()              { return login; }
  public String  getSenha()              { return senha; }
  public LocalDateTime getCriadoEm()     { return criadoEm; }
  public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
  public List<Usuario> getUsuarios()     { return usuarios; }
  public List<Item>    getItens()        { return itens; }



  // SETTERS
  public void setId(Integer id)                { this.id = id; }
  public void setNome(String nome)             { this.nome = nome.trim(); }
  public void setLogin(String login)           { this.login = login.trim().toLowerCase(); }
  public void setSenha(String senha)           { this.senha = senha; }
  public void setAtualizadoEm(LocalDateTime dt){ this.atualizadoEm = dt; }
}