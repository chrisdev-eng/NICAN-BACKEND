package com.faculdade.projeto.login.classes;

import java.util.ArrayList;
import java.util.List;

/**
 *  ~ Repositorio em memoria de Usuarios ~
 *
 *  Segue o mesmo padrao do ListaItems.java do almoxarife.
 *  Troque por JDBC/JPA para persistencia real no futuro.
 *
 *  Ja vem com um Admin padrao criado para o primeiro acesso:
 *    Email: admin@nican.com  |  Senha: Admin123
 */
public class ListaUsuarios {

  private static List<Usuario> lista = new ArrayList<>();




  //  ~ Bloco estatico: cria o Admin padrao ao carregar a classe ~
  static {
    Usuario adminPadrao = new Usuario(
      "Administrador",
      "admin@nican.com",
      "Admin123",
      Perfil.ADMIN
    );
    lista.add(adminPadrao);
  }




  //  ~ Adiciona usuario se o email ainda nao existir ~
  public static boolean adicionarUsuario(Usuario novo) {
    if (buscarPorEmail(novo.getEmail()) != null) {
      return false;  //  ~ Email ja cadastrado
    }
    lista.add(novo);
    return true;
  }




  //  ~ Busca por email (retorna null se nao encontrar) ~
  public static Usuario buscarPorEmail(String email) {
    for (Usuario u : lista) {
      if (u.getEmail().equals(email.trim().toLowerCase())) {
        return u;
      }
    }
    return null;
  }




  //  ~ Busca por ID ~
  public static Usuario buscarPorId(int id) {
    for (Usuario u : lista) {
      if (u.getId() == id) return u;
    }
    return null;
  }




  public static List<Usuario> getLista()    { return lista;        }
  public static int           getTamanho()  { return lista.size(); }




  public static void listarTodos() {
    System.out.println("\n\n====== Lista de Usuarios ======\n");
    for (Usuario u : lista) {
      u.infosUsuario();
    }
  }
}
