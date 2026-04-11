package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.login.classes.Admin;
import com.faculdade.projeto.models.JPAUtils;
import jakarta.persistence.EntityManager;
import java.util.List;

/*
 *  ~ Operações de CRUD para a tabela de Requerimentos ~
 *
 *  CORREÇÕES:
 *    - aprovarRequerimento agora recebe Admin (estava recebendo Usuario)
 *  ADIÇÕES:
 *    - listarPorUsuario() com JOIN FETCH — mais um join para o Critério I
 *    - recusarRequerimento() — complementa as regras de negócio
 */
public class ListaRequerimentos {



  // CRUD — Salvar novo requerimento
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



  // CRUD — Buscar requerimentos pendentes (com JOIN FETCH de item e usuário)
  // JOIN FETCH = Critério I
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



  // CRUD — Listar todos os requerimentos de um usuário específico
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



  // CRUD — Atualizar status do requerimento
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



  // REGRA DE NEGÓCIO: aprovar requerimento e baixar estoque automaticamente
  // REGRA DE NEGÓCIO: somente Admin pode aprovar
  public static boolean aprovar(Requerimento req, Admin admin) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      // REGRA DE NEGÓCIO: valida estoque antes de aprovar
      Item item = req.getItem();
      if (item == null || item.getQuantidadeDisponivel() < req.getQuantidadeSolicitada()) {
        System.out.println("\n  [ERRO] Estoque insuficiente para aprovação.\n");
        em.getTransaction().rollback();
        return false;
      }

      req.aprovar(admin);
      item.diminuirQuant(req.getQuantidadeSolicitada());

      em.merge(req);
      em.merge(item);
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



  // REGRA DE NEGÓCIO: recusar requerimento (sem alterar estoque)
  public static boolean recusar(Requerimento req, Admin admin) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
      req.recusar(admin);
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
}
