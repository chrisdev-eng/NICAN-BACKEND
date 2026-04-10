package com.faculdade.projeto.login.classes;

import com.faculdade.projeto.models.JPAUtils;  //  ~ Aq chama o arquivo q faz a comunication com o JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;




/**
 *  ~ Classe que cuida da Lista de Usuarios (Agora em tabela) ~
 *
 *  Como na classe Usuario ja cuida dessa parte de relacionar a tabela 
 *  com o codigo, aqui a gente so cuida das relacoes de CRUD
 *  (Criar, Alterar, Remover da tabela, alem de buscas e coisas "simples")
 */
public class ListaUsuarios {




  //  ~ Metodo de Salvar um novo usuario
  public static boolean salvar(Usuario usuario) {
    


    EntityManager em = JPAUtils.getEntityManager();   //  ~ Entidade d conexao com o JPAUtils;
        try {
            em.getTransaction().begin();
            
            // Verifica duplicidade de login (Regra de Negócio)
            TypedQuery<Long> qtd = em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login", Long.class);
            qtd.setParameter("login", usuario.getLogin());
            if (qtd.getSingleResult() > 0) {
                em.getTransaction().rollback();
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



    //  ~ Email -> Login 
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

    public static Usuario buscarPorId(Integer id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public static List<Usuario> listarTodos() {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u ORDER BY u.nome", Usuario.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

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
}
