package com.faculdade.projeto.login.classes;



import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

/*
 *  Classe de Usuario do Sistema.
 *
 *  CORREÇÕES APLICADAS:
 *    - @JoinColumn(name = "idAdmin_fk") — corrigido para bater com o nome real da coluna no banco
 *    - nullable = true pois o cadastro inicial pode nao ter admin logado
 *    - Colunas "perfil" e "ativo" adicionadas ao @Column para mapear corretamente
 */
@Entity
@Table(name = "usuario")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idUsuario")
  private Integer id;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "login", nullable = false, length = 100, unique = true)
  private String login;

  @Column(name = "senha", nullable = false, length = 255)
  private String senha;

  // CORRECAO: nome da coluna era "idAdmin", o correto e "idAdmin_fk" (igual ao banco)
  // nullable = true pois o primeiro usuario pode ser cadastrado sem admin previo
  @ManyToOne
  @JoinColumn(name = "idAdmin_fk", nullable = true)
  private Admin adminResponsavel;

  @Enumerated(EnumType.STRING)
  @Column(name = "perfil", nullable = false)
  private Perfil perfil;

  @CreationTimestamp
  @Column(name = "criadoEm", updatable = false)
  private LocalDateTime criadoEm;

  @UpdateTimestamp
  @Column(name = "atualizadoEm")
  private LocalDateTime atualizadoEm;

  @Column(name = "ativo")
  private boolean ativo;



  public Usuario(String nome, String login, String senha, Perfil perfil, Admin adminResponsavel) {
    this.nome = nome.trim();
    this.login = login.trim().toLowerCase();
    this.senha = senha;
    this.perfil = perfil;
    this.adminResponsavel = adminResponsavel;
    this.ativo = true;
  }

  // Constructor vazio obrigatorio para o Hibernate
  public Usuario() {}



  // GETTERS
  public Integer getId()                       { return id; }
  public String  getNome()                     { return nome; }
  public String  getLogin()                    { return login; }
  public String  getSenha()                    { return senha; }
  public Perfil  getPerfil()                   { return perfil; }
  public boolean isAtivo()                     { return ativo; }
  public boolean isAdmin()                     { return perfil == Perfil.ADMIN; }
  public Admin   getAdminResponsavel()         { return adminResponsavel; }
  public LocalDateTime getCriadoEm()           { return criadoEm; }
  public LocalDateTime getAtualizadoEm()       { return atualizadoEm; }



  // SETTERS
  public void setId(Integer id)                            { this.id = id; }
  public void setNome(String nome)                         { this.nome = nome.trim(); }
  public void setLogin(String login)                       { this.login = login.trim().toLowerCase(); }
  public void setSenha(String senha)                       { this.senha = senha; }
  public void setPerfil(Perfil perfil)                     { this.perfil = perfil; }
  public void setAtivo(boolean ativo)                      { this.ativo = ativo; }
  public void setAdminResponsavel(Admin adminResponsavel)  { this.adminResponsavel = adminResponsavel; }
  public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }



  // MÉTODOS
  public void infosUsuario() {
    System.out.println("\n~ " + getNome() + " ~");
    System.out.println("[ ID ]: " + getId());
    System.out.println("Login: " + getLogin());
    System.out.println("Perfil: " + getPerfil());
    System.out.println("Status: " + (isAtivo() ? "Ativo" : "Inativo"));
    System.out.println();
  }
}
