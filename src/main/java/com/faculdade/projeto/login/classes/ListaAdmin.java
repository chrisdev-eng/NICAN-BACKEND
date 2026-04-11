package com.faculdade.projeto.login.classes;

import com.faculdade.projeto.models.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/*
 *  ~ Operações de CRUD para a tabela Admin ~
 *
 *  Criada para complementar o sistema, já que Admin é uma entidade separada de Usuario.
 *  Necessária principalmente para buscar o Admin correto ao criar itens no almoxarife.
 *
 *  ADIÇÃO NOVA — não existia no projeto original.
 */
public class ListaAdmin {



  // CRUD — Salvar novo admin
  // REGRA DE NEGÓCIO: login de admin deve ser único no sistema
  public static boolean salvar(Admin admin) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      // REGRA DE NEGÓCIO: verifica duplicidade de login
      TypedQuery<Long> qtd = em.createQuery(
          "SELECT COUNT(a) FROM Admin a WHERE a.login = :login", Long.class);
      qtd.setParameter("login", admin.getLogin());
      if (qtd.getSingleResult() > 0) {
        System.out.println("  [ERRO] Login de admin já cadastrado.");
        em.getTransaction().rollback();
        return false;
      }

      em.persist(admin);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
      return false;
    } finally {
      em.close();
    }
  }



  // CRUD — Buscar admin por ID (usado ao criar itens, para passar o Admin correto)
  public static Admin buscarPorId(Integer id) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.find(Admin.class, id);
    } finally {
      em.close();
    }
  }



  // CRUD — Buscar admin por login
  public static Admin buscarPorLogin(String login) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      TypedQuery<Admin> query = em.createQuery(
          "SELECT a FROM Admin a WHERE a.login = :login", Admin.class);
      query.setParameter("login", login.trim().toLowerCase());
      return query.getResultStream().findFirst().orElse(null);
    } finally {
      em.close();
    }
  }



  // CRUD — Listar todos os admins
  public static List<Admin> listarTodos() {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery("SELECT a FROM Admin a ORDER BY a.nome", Admin.class)
               .getResultList();
    } finally {
      em.close();
    }
  }



  // CRUD — Atualizar admin
  public static boolean atualizar(Admin admin) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(admin);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
      return false;
    } finally {
      em.close();
    }
  }
}
