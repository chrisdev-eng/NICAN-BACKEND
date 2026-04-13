package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.models.JPAUtils;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

/*
 *  ~ Operações de CRUD para a tabela almoxarifado (Item) ~
 *
 *  ADIÇÕES:
 *    - JOIN FETCH em getListaItems() para carregar adminResponsavel junto com o item
 *      → Critério I: join implementado em JPQL
 */
public class ListaItems {



  // CRUD — Listar todos os itens (com JOIN FETCH do admin responsável)
  public static List<Item> getListaItems() {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.createQuery(
          "SELECT i FROM Item i JOIN FETCH i.adminResponsavel ORDER BY i.nomeItem",
          Item.class
      ).getResultList();
    } finally {
      em.close();
    }
  }



  // CRUD — Buscar item por ID
  public static Item buscarPorId(Integer id) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      return em.find(Item.class, id);
    } finally {
      em.close();
    }
  }



  // CRUD — Adicionar novo item
  public static void adicionarItem(Item novoItem) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(novoItem);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
    } finally {
      em.close();
    }
  }



  // CRUD — Remover item
  // REGRA DE NEGÓCIO: item só pode ser removido se não houver requerimentos pendentes
  public static void removerItem(Item item) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      // REGRA DE NEGÓCIO: verifica se há requerimentos pendentes para este item
      Long pendentes = em.createQuery(
          "SELECT COUNT(r) FROM Requerimento r WHERE r.item.idItem = :id AND r.status = 'pendente'",
          Long.class
      ).setParameter("id", item.getIdItem()).getSingleResult();

      if (pendentes > 0) {
        System.out.println("\n  [ERRO] Não é possível remover: item possui " + pendentes + " requerimento(s) pendente(s).\n");
        em.getTransaction().rollback();
        return;
      }

      Item gerenciado = em.find(Item.class, item.getIdItem());
      if (gerenciado != null) em.remove(gerenciado);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
    } finally {
      em.close();
    }
  }



  // CRUD — Atualizar item existente
  public static boolean atualizar(Item item) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();
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



  // Utilitário: sub-menu de seleção de item por ID
  public static Item getItemLista(Scanner leitor) {
    List<Item> lista = getListaItems();
    if (lista.isEmpty()) {
      System.out.println("Nenhum item cadastrado.");
      return null;
    }
    for (Item i : lista) i.infosGeralItem();

    System.out.print("Digite o ID do item: ");
    try {
      int id = leitor.nextInt();
      leitor.nextLine();
      return buscarPorId(id);
    } catch (Exception e) {
      System.out.println("Entrada inválida.");
      leitor.nextLine();
      return null;
    }
  }
}
