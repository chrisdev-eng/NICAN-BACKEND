package com.faculdade.nican.model.repository;

import com.faculdade.nican.model.config.JPAUtils;
import com.faculdade.nican.model.entity.*; import com.faculdade.nican.model.service.*; import com.faculdade.nican.model.repository.*;
import jakarta.persistence.EntityManager;
import java.util.List;

public class RequerimentoRepository {

  public static boolean salvar(Requerimento req) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(req);
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

  public static List<Requerimento> buscarPendentes() {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery(
          "SELECT r FROM Requerimento r " +
          "JOIN FETCH r.usuario " +
          "JOIN FETCH r.item " +
          "WHERE r.status = 'pendente'",
          Requerimento.class
      ).getResultList();
    } finally {
      em.close();
    }
  }

  public static List<Requerimento> listarPorUsuario(Integer idUsuario) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery(
          "SELECT r FROM Requerimento r " +
          "JOIN FETCH r.item " +
          "WHERE r.usuario.id = :id ORDER BY r.dataSolicitacao DESC",
          Requerimento.class
      ).setParameter("id", idUsuario).getResultList();
    } finally {
      em.close();
    }
  }

  public static boolean atualizarStatus(Requerimento req) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
      em.merge(req);
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

  public static boolean aprovar(Requerimento req, Admin admin) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      Requerimento gerenciado = em.find(Requerimento.class, req.getIdRequerimento());
      Admin adminGerenciado = admin != null ? em.find(Admin.class, admin.getId()) : null;

      if (gerenciado == null || adminGerenciado == null || !"pendente".equalsIgnoreCase(gerenciado.getStatus())) {
        em.getTransaction().rollback();
        return false;
      }

      Item item = gerenciado.getItem();
      if (item == null || item.getQuantidadeDisponivel() < gerenciado.getQuantidadeSolicitada()) {
        System.out.println("\n  [ERRO] Estoque insuficiente para aprovacao.\n");
        em.getTransaction().rollback();
        return false;
      }

      // O trigger trigger_aprovacao_requerimento cria o emprestimo, e
      // trigger_retirada_item baixa o estoque. Nao baixamos no Java para evitar duplicidade.
      gerenciado.aprovar(adminGerenciado);
      em.merge(gerenciado);
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

  public static boolean recusar(Requerimento req, Admin admin) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      Requerimento gerenciado = em.find(Requerimento.class, req.getIdRequerimento());
      Admin adminGerenciado = admin != null ? em.find(Admin.class, admin.getId()) : null;

      if (gerenciado == null || adminGerenciado == null || !"pendente".equalsIgnoreCase(gerenciado.getStatus())) {
        em.getTransaction().rollback();
        return false;
      }

      gerenciado.recusar(adminGerenciado);
      em.merge(gerenciado);
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
