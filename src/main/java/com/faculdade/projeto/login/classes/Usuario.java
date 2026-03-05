package com.faculdade.projeto.login.classes;

/**
 *  ~ Representa um Usuario do Sistema Nican ~
 *
 *  Cada usuario tem: nome, email, senha e um Perfil (USUARIO ou ADMIN).
 *  O ID e gerado automaticamente na criacao.
 */
public class Usuario {

  private static int contadorId = 1;  //  ~ Contador estatico para gerar IDs unicos
  private final int    id;
  private       String nome;
  private       String email;
  private       String senha;
  private       Perfil perfil;
  private       boolean ativo;




  public Usuario(String nome, String email, String senha, Perfil perfil) {
    this.id     = contadorId++;
    this.nome   = nome.trim();
    this.email  = email.trim().toLowerCase();
    this.senha  = senha;
    this.perfil = perfil;
    this.ativo  = true;
  }




  //  ~ GETTERS ~
  public int     getId()     { return id;     }
  public String  getNome()   { return nome;   }
  public String  getEmail()  { return email;  }
  public String  getSenha()  { return senha;  }
  public Perfil  getPerfil() { return perfil; }
  public boolean isAtivo()   { return ativo;  }
  public boolean isAdmin()   { return perfil == Perfil.ADMIN; }




  //  ~ SETTERS ~
  public void setNome(String nome)     { this.nome  = nome.trim();              }
  public void setEmail(String email)   { this.email = email.trim().toLowerCase(); }
  public void setSenha(String senha)   { this.senha = senha;                    }
  public void setPerfil(Perfil perfil) { this.perfil = perfil;                  }
  public void setAtivo(boolean ativo)  { this.ativo  = ativo;                   }




  public void infosUsuario() {
    System.out.println("\n~ " + getNome() + " ~");
    System.out.println("[ ID ]: "   + getId());
    System.out.println("Email: "    + getEmail());
    System.out.println("Perfil: "   + getPerfil());
    System.out.println("Status: "   + (isAtivo() ? "Ativo" : "Inativo"));
    System.out.println();
  }
}
