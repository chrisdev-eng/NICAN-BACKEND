package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.models.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;






//  ~ Classe de Cuida dos itens do Sistema ~
//
//  Originalmente, guardavamos em ArrayList nesta classe, mas agora, aqui e onde cuida 
//  dos processos de CRUD dos itens do sistema...
//
//
public class ListaItems {
      




  //  ~ Pega todos os itens da lista...
  public static List<Item> getListaItems() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Item i ORDER BY i.nomeItem", Item.class).getResultList();
        } finally { em.close(); }
    }
  //  ~ Pega um item by seu id 
  public static Item buscarPorId(Integer id) {
      EntityManager em = JPAUtils.getEntityManager();
      try {
          return em.find(Item.class, id);
        } 
      finally { em.close(); }
    }






  //  ~ Add
    public static void adicionarItem(Item novoItem) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(novoItem);
            em.getTransaction().commit();
        } catch (Exception e) { 
            em.getTransaction().rollback(); 
            e.printStackTrace();
        } finally { em.close(); }
    }





    //  ~ Remove
    public static void removerItem(Item item) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            // Precisamos buscar o item gerenciado antes de remover
            Item gerenciado = em.find(Item.class, item.getIdItem());
            if (gerenciado != null) em.remove(gerenciado);
            em.getTransaction().commit();
        } catch (Exception e) { 
            em.getTransaction().rollback(); 
            e.printStackTrace();
        } finally { em.close(); }
    }




    
    public static boolean atualizar(Item item) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(item); // merge atualiza ou insere se não existir
            em.getTransaction().commit();
            return true;
        } catch (Exception e) { 
            em.getTransaction().rollback(); 
            e.printStackTrace();
            return false;
        } finally { em.close(); }
    }








    //  ~ Cria um "sub-menu" p escolher um item
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
            return buscarPorId(id); // Mais eficiente que filtrar lista em memória
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
            leitor.nextLine();
            return null;
        }
    }


}
