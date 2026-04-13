package com.faculdade.projeto.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtils {
  //  ~ Variavel do tipo static, para q possa ser acessada, sem precisar "instanciar" o objeto
  private static EntityManagerFactory emf;



  public static EntityManager getEntityManager() {
    //  ~ Isso daq serve pra nao ter q inicializar o EntityManager toda vez q chamar
    if (emf == null) {
      //  ~ Inicializa o EntityManagerFactory, que interpreta e cria uma conexao com o banco
      //  ~ pelas infos do persistence.xml
      emf = Persistence.createEntityManagerFactory("nicandb");
    }

    return emf.createEntityManager();
  }



  //  ~ Fecha o EntityManagerFactory
  public static void close() {
    if (emf != null) emf.close();
  }
}
