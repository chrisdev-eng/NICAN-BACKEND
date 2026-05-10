package com.faculdade.nican.repository;

import com.faculdade.nican.model.*;
import com.faculdade.nican.config.JPAUtils;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  ~ CRUD para a tabela emprestar ~
 *
 *  NOTA: o registro de empréstimo é criado automaticamente via trigger no banco
 *  quando um requerimento é aprovado. Aqui gerenciamos consultas e devoluções.
 */
public class EmprestimoRepository {

  // CRUD — Buscar todos os empréstimos em aberto (sem dataDev)
  public static List<Emprestimo> buscarEmAberto() {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery(
          "SELECT e FROM Emprestimo e " +
          "JOIN FETCH e.usuario " +
          "JOIN FETCH e.item " +
          "WHERE e.dataDev IS NULL " +
          "ORDER BY e.devPrevista ASC",
          Emprestimo.class
      ).getResultList();
    } finally {
      em.close();
    }
  }

  // CRUD — Buscar empréstimos de um usuário específico (histórico)
  public static List<Emprestimo> buscarPorUsuario(Integer idUsuario) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery(
          "SELECT e FROM Emprestimo e " +
          "JOIN FETCH e.item " +
          "WHERE e.usuario.id = :id " +
          "ORDER BY e.dataPegou DESC",
          Emprestimo.class
      ).setParameter("id", idUsuario).getResultList();
    } finally {
      em.close();
    }
  }

  // CRUD — Buscar empréstimos em aberto de um usuário (para confirmar devolução)
  public static List<Emprestimo> buscarEmAbertoDoUsuario(Integer idUsuario) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery(
          "SELECT e FROM Emprestimo e " +
          "JOIN FETCH e.item " +
          "WHERE e.usuario.id = :id AND e.dataDev IS NULL " +
          "ORDER BY e.devPrevista ASC",
          Emprestimo.class
      ).setParameter("id", idUsuario).getResultList();
    } finally {
      em.close();
    }
  }

  // CRUD — Buscar por ID
  public static Emprestimo buscarPorId(Integer id) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.find(Emprestimo.class, id);
    } finally {
      em.close();
    }
  }

  // REGRA DE NEGÓCIO: registrar devolução de um empréstimo
  // Atualizar dataDev dispara o trigger retorno_estoque_almoxarifado no banco,
  // que devolve a quantidade ao estoque automaticamente.
  public static boolean registrarDevolucao(Emprestimo emp, String estadoItem, String obs) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      Emprestimo gerenciado = em.find(Emprestimo.class, emp.getIdEmprestimo());
      if (gerenciado == null) return false;

      gerenciado.setDataDev(LocalDate.now());
      gerenciado.setEstadoItem(estadoItem);
      gerenciado.setObsEstado(obs != null && !obs.isBlank() ? obs : null);
      gerenciado.setAtualizadoEm(LocalDateTime.now());

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
