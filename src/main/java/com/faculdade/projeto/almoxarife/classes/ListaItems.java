package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.models.JPAUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;






//  ~ Classe de Cuida dos itens do Sistema ~
//
//  Originalmente, guardavamos em ArrayList nesta classe, mas agora, aqui e onde cuida 
//  dos processos de CRUD dos itens do sistema...
//
//
public class ListaItems {
  

  //  ~ Metodo de salvar u7m novo item no banco de dados 
  public static boolean salvar(Item item) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
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


    //  ~ Lista td os itens 
    public static List<Item> getListaItems() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            TypedQuery<Item> query = em.createQuery("SELECT i FROM Item i", Item.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Buscar por ID
    public static Item buscarPorId(Integer id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }
    
    // Atualizar quantidade (exemplo de merge)
    public static boolean atualizar(Item item) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(item);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
}
