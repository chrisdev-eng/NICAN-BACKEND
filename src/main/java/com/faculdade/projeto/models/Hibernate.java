package com.faculdade.projeto.models;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




//  ~ Cria uma conexao unica com o Banco de Dados, para nao criar "varias" conexoes com o banco d dados;
//  ~ Serve p nao sobrecarregar/criar mtas conexoes e ficar pesado e talz...
public class Hibernate {


  private static SessionFactory sessionHbAtual;



  //  ~ Esse metodo executa uma chamada ao banco por vez, ao ser chamado varias vezes, ele cria uma fila de 
  //  ~ conexoes com o banco
  public static synchronized SessionFactory getSessionHbAtual() {
    //  ~ Retorna a Session atual do Hibernate (  Se n tiver, ele cria uma  )
    if (  sessionHbAtual == null  ) {
      try {
        sessionHbAtual = new Configuration().configure("hibernate-cfg.xml").buildSessionFactory();
        System.out.println("Hibernate Inicializado...");
      }
      catch (  Throwable ex  ) {
        System.out.println("Erro ao criar sessao do Hibernate " + ex.getMessage());
        throw new ExceptionInInitializerError(ex);
      }
    }

    return sessionHbAtual;
  }


  public static void fecharSessionHbAtual() {
    if (  sessionHbAtual != null  ) {
      sessionHbAtual.close();
      System.out.println("✅ Hibernate fechado.");
    }
  } 
}
