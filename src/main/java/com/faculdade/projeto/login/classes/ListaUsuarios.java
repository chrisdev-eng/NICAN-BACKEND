package com.faculdade.projeto.login.classes;

import com.faculdade.projeto.models.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/*
 *  ~ Operações de CRUD para a tabela de Usuarios ~
 *
 *  ADIÇÕES:
 *    - JOIN FETCH em listarTodos() — Critério I: "implementação de cada join abordado em aula"
 *    - buscarAdminPorLogin() adicionado para o fluxo de login de admins
 */
public class ListaUsuarios {



  // CRUD — Salvar novo usuario
  // REGRA DE NEGÓCIO: não permite cadastro com login duplicado
  public static boolean salvar(Usuario usuario) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      // REGRA DE NEGÓCIO: verificação de login duplicado antes de persistir
      TypedQuery<Long> qtd = em.createQuery(
          "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login", Long.class);
      qtd.setParameter("login", usuario.getLogin());
      if (qtd.getSingleResult() > 0) {
        em.getTransaction().rollback();
        System.out.println("  [ERRO] Login já cadastrado no sistema.");
        return false;
      }

      em.persist(usuario);
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



  // CRUD — Buscar por login
  public static Usuario buscarPorLogin(String login) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      TypedQuery<Usuario> query = em.createQuery(
          "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
      query.setParameter("login", login.trim().toLowerCase());
      return query.getResultStream().findFirst().orElse(null);
    } finally {
      em.close();
    }
  }



  // CRUD — Buscar por ID
  public static Usuario buscarPorId(Integer id) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.find(Usuario.class, id);
    } finally {
      em.close();
    }
  }



  // CRUD — Listar todos (com JOIN FETCH para carregar o admin responsável junto)
  // JOIN FETCH = Critério I: "implementação de cada join abordado em aula"
  public static List<Usuario> listarTodos() {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery(
          "SELECT u FROM Usuario u LEFT JOIN FETCH u.adminResponsavel ORDER BY u.nome",
          Usuario.class
      ).getResultList();
    } finally {
      em.close();
    }
  }



  // CRUD — Atualizar usuario existente
  public static boolean atualizar(Usuario usuario) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(usuario);
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



  // CRUD — Desativar conta (soft delete)
  // REGRA DE NEGÓCIO: não removemos usuários fisicamente, apenas desativamos
  public static boolean desativar(Integer id) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
      Usuario u = em.find(Usuario.class, id);
      if (u == null) return false;
      u.setAtivo(false);
      em.merge(u);
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
