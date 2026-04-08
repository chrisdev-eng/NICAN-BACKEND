package com.faculdade.projeto.login.classes;


import com.faculdade.projeto.login.classes.Admin;
import org.hibernate.annotations.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;


/*  
 *  Classe de Usuario do Sistema.
 *
 *  Conecta a tabela do banco do PostgreSQL pelos metodos do JPA. 
 *  Ao definir @Entity, ele conecta com o Hibernate-cfg.xml que configuramos, e diz que essa classe representara
 *  a tabela usuario do banco.
 */
@Entity
@Table(name = "usuario")
public class Usuario {



  //  ~ Definimos que a variavel vai ser um do tipo de um ID, 
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)     //  ~ Diz que e do tipo serial (auto incremento de id)
  @Column(name = "idUsuario")                             //  ~ Nome da Coluna que vai se relacionar      
  private Integer id;
  
  @Column(name = "nome", nullable = false, length = 100)  //  ~ Variavel do tipo Varchar, que n pode ser null. 
  private String nome;

  @Column(name = "login", nullable = false, length = 100, unique = true)   //  ~ Mudanca de Email -> Login 
  private String login;
  
  @Column(name = "senha", nullable = false, length = 255)
  private String senha;


  @ManyToOne                                              //  ~ ManyToOne pra indicar que varios users podem ter o msm id de admin ao criar...  
  @JoinColumn(name = "adminResponsavel", nullable = false)         //  ~ Criacao da FK pro Admin que gerou esse Usuario...
  private Admin adminResponsavel;


  @Enumerated(  EnumType.STRING  )                        //  ~ Salva o perfil de tipo Usuario ou Admin; 
  @Column(name = "perfil", nullable = false)
  private Perfil perfil;



  @CreationTimestamp                                      //  ~ Ah esses 2 aq serve pra ter uma nocao da hora de criacao/atualizacao do user
  @Column(name = "criadoEm", updatable = false)
  private LocalDateTime criadoEm;

  @CreationTimestamp
  @Column(name = "atualizadoEm")
  private LocalDateTime atualizadoEm;



  @Column(name = "ativo")  
  private boolean ativo;





  //  ~ Constructor do Usuario Ajustado pros novos itens alocados do JPA...
  public Usuario(String nome, String login, String senha, Perfil perfil, Admin adminResponsavel) {
    this.nome = nome.trim();
    this.login = login.trim().toLowerCase();
    this.senha = senha;
    this.perfil = perfil;
    this.adminResponsavel = adminResponsavel;
    this.ativo = true;
  }
  
  //  ~ O hibernate exige um constructor vazio, pois primeiro, ao rodar o codigo, precisa executar a classe
  //  ~ de entidade sem a passagem de parametros ("definir"), e dps pode ser usado um constructor (como classe normal) 
  public Usuario() {}






  //  ~ GETTERS ~
  public Integer getId()     {  return id;      }
  public String  getNome()   {  return nome;    }
  public String  getLogin()  {  return login;   }
  public String  getSenha()  {  return senha;   }
  public Perfil  getPerfil() {  return perfil;  }
  public boolean isAtivo()   {  return ativo;   }
  public boolean isAdmin()   {  return perfil == Perfil.ADMIN; }
  public Admin getAdminResponsavel() {  return adminResponsavel; }
  public LocalDateTime getCriadoEm() {  return criadoEm;  }
  public LocalDateTime getAtualizadoEm() {  return atualizadoEm;  }


  //  ~ SETTERS ~
  public void setId(  Integer id  )         {  this.id = id; }
  public void setNome(  String nome  )      {  this.nome  = nome.trim();                }
  public void setLogin(  String email  )    {  this.login = email.trim().toLowerCase(); }
  public void setSenha(  String senha  )    {  this.senha = senha;                      }
  public void setPerfil(  Perfil perfil  )  {  this.perfil = perfil;                    }
  public void setAtivo(  boolean ativo  )   {  this.ativo  = ativo;                     }
  public void setAdminResponsavel(  Admin adminResponsavel )  {  this.adminResponsavel = adminResponsavel;  }
  public void setAtualizadoEm(LocalDateTime atualizadoEm)     {  this.atualizadoEm = atualizadoEm;  }




  //  ~ METODO's ~
  public void infosUsuario() {
    System.out.println("\n~ " + getNome() + " ~");
    System.out.println("[ ID ]: "   + getId());
    System.out.println("Email: "    + getLogin());
    System.out.println("Perfil: "   + getPerfil());
    System.out.println("Status: "   + (isAtivo() ? "Ativo" : "Inativo"));
    System.out.println();
  }
}
