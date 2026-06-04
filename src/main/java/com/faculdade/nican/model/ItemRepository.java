package com.faculdade.nican.model;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

/*
 *  ~ Operações de CRUD para a tabela almoxarifado (Item) ~
 */
public class ItemRepository {



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



  // CRUD — Excluir item completo do sistema
  // REGRA DE NEGÓCIO: item só pode ser excluído se não houver nenhum requerimento vinculado
  public static boolean removerItem(Item item) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      em.getTransaction().begin();

      Long vinculados = em.createQuery(
              "SELECT COUNT(r) FROM Requerimento r WHERE r.item.idItem = :id",
              Long.class
      ).setParameter("id", item.getIdItem()).getSingleResult();

      if (vinculados > 0) {
        em.getTransaction().rollback();
        return false;
      }

      Item gerenciado = em.find(Item.class, item.getIdItem());
      if (gerenciado != null) em.remove(gerenciado);
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



  // NOVO: almoxarife baixa estoque físico (item danificado, perdido, etc)
  public static String removerQuantidade(int idItem, int qtd) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      Item item = em.find(Item.class, idItem);
      if (item == null) return "Item não encontrado.";

      em.getTransaction().begin();
      String erro = item.diminuirTotal(qtd);
      if (erro != null) {
        em.getTransaction().rollback();
        return erro;
      }
      em.merge(item);
      em.getTransaction().commit();
      return null;
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
      return "Erro ao remover quantidade: " + e.getMessage();
    } finally {
      em.close();
    }
  }



  // NOVO: almoxarife adiciona estoque físico
  public static String adicionarQuantidade(int idItem, int qtd) {
    EntityManager em = JPAUtils.getEntityManager();
    try {
      Item item = em.find(Item.class, idItem);
      if (item == null) return "Item não encontrado.";
      if (qtd <= 0) return "A quantidade deve ser maior que zero.";

      em.getTransaction().begin();
      item.aumentarTotal(qtd);
      em.merge(item);
      em.getTransaction().commit();
      return null;
    } catch (Exception e) {
      em.getTransaction().rollback();
      e.printStackTrace();
      return "Erro ao adicionar quantidade: " + e.getMessage();
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