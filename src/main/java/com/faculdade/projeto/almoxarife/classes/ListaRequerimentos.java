package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.models.JPAUtils;
import jakarta.persistence.EntityManager;
import java.util.List;





/*
 *  ~ Operacoes com os Requerimentos ~
 *  Aqui e onde fica a parte responsavel por cuidar dos requerimentos
 *
 *
 */
public class ListaRequerimentos {

   

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
            // JPQL para buscar apenas os pendentes
            return em.createQuery("SELECT r FROM Requerimento r WHERE r.status = 'pendente'", Requerimento.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }




    public static boolean atualizarStatus(Requerimento req) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            // O merge atualiza o registro existente
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
