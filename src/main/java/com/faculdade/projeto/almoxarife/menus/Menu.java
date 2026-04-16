package com.faculdade.projeto.almoxarife.menus;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.classes.*;
import com.faculdade.projeto.login.classes.*;




public class Menu {
  
  //  ~ Controler se e p roda o menu de almoxarife...
  private static boolean menuAlmoxarife = true;




  public static void main(String[] args, ListaItems lista, Scanner leitor) {    
    int escolhaMenu = 0;
    

    do {
      System.out.println("\n\n=== MENU ALMOXARIFE ===\n");
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Ver lista de Materiais da Sede.");
      System.out.println("[2] ~ Adicionar/Remover algum Material.");
      System.out.println("[3] ~ Requerimento de Algum Material.");
      System.out.println("[4] ~ Validar Requerimentos de Materiais");
      System.out.println("\n[3] ~ Voltar ao Menu Principal");
      System.out.println("\n\n");



      try {
        
        escolhaMenu = leitor.nextInt();
        

        switch (  escolhaMenu  ) {
          //  ~ Agora nao vamos mais passar a lista de passavamos pelos metodos, pois puxaremos da classe/banco automatico...
          case 1:
            listarMateriais(  leitor  );
            break;
          case 2:
            //  ~ Somentes admins podem realizar crud dos itens...
            if (  !Sessao.get().usuarioEhAdmin()  ) {
              System.out.println("\n SOMENTE ADMINS...\n");
              break;
            }
            adicionarRemover(  leitor  );
            break;
          case 3:
            fazerRequerimento(  leitor  );
            break;
          case 4:
            validarRequerimentos(leitor);
            break;
          default:  
            menuAlmoxarife = false;
            System.out.println("\nVoltando... \n\n\n\n");
            break;
        }
      } 
      //  ~ Caso algo alem de numeros seja digitado, ele cai p aq
      catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Tentando novamente... ");
        leitor.nextLine();
      }
    } while (  menuAlmoxarife  ); 
  }








  //  ~ Opcao 1 do Menu;
  private static void listarMateriais(  Scanner leitor  ) {  
    
    boolean subMenu = true;
    int escolhaMenu = 0;
    

    do {
      System.out.println("\n\n====== Lista de Materiais ======\n"); 
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Listar Todos os Materiais.");
      System.out.println("[2] ~ Listar os Materiais por Categoria.");
      System.out.println("[3] ~ Listar os Materiais por Estado.");
      System.out.println("\n[4] ~ Voltar... ");
      System.out.println("\n\n");


      try {

        escolhaMenu = leitor.nextInt();
      

        switch (  escolhaMenu  ) {
          case 1:
            MenuListas.listarTudo(); 
            break;
          case 2:
            MenuListas.listarMenuCategoria();
            break;
          case 3:
            MenuListas.listarMenuEstado();
            break;
          default:
            subMenu = false;
            System.out.println("\nVoltando... \n\n\n\n");
            break;
        }
      }
      catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Tentando novamente... ");
        leitor.nextLine();
      } 
      catch (  Exception exception  ) {
        System.out.println("Deu erro aq:" + exception.getMessage() + " :/");
      }
    } while (  subMenu  );
  }







  //  ~ Opcao 2 do menu Principal...
  private static void adicionarRemover(  Scanner leitor  ) {
    
    boolean subMenu = true;
    int escolhaMenu = 0;
  


    do {
      System.out.println("\n\n====== Adicionar / Remover Itens ======\n"); 
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Adicionar Materiais.");
      System.out.println("[2] ~ Remover Materiais.");
      System.out.println("[3] ~ Mudar Quantidade de algum item.");
      System.out.println("\n[4] ~ Voltar... ");
      System.out.println("\n\n");


      try {
        escolhaMenu = leitor.nextInt();

        switch (  escolhaMenu  ) {


          //  ~ Adicionar Item
          case 1:
            MenuItens.adicionarItem(  leitor  );
            break;


          //  ~ Remover Item
          case 2:
            MenuItens.removerItem(  leitor  );
            break;


          //  ~ Mudar Quantidade
          case 3:
            MenuItens.mudarQuantidade(leitor);
            break;


          default:
            subMenu = false;
            System.out.println("\nVoltando... \n\n\n\n");
            break;
        }
      }
      catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Tentando novamente... ");
        leitor.nextLine();
      }
    } while (  subMenu  );

  }








  //  ~ Opcao 3 do Menu, Fazer um Requerimento;
  private static void fazerRequerimento(Scanner leitor) {
    try {

       //  ~  verifica se ta logado...
        Sessao sessao = Sessao.get();
        if (!sessao.estaLogado()) {
            System.out.println("\n[ERRO] Faça login para solicitar materiais.\n");
            return;
        }



        //  ~ E verifica se tem material agr...
        List<Item> itens = ListaItems.getListaItems();
        if (itens.isEmpty()) {
            System.out.println("\n[AVISO] Estoque vazio. Nenhum item disponível para requisição.\n");
            return;
        }





        System.out.println("\n====== Solicitar Material ======\n");
        for (Item i : itens) {
            System.out.println("ID " + i.getIdItem() + " | " + i.getNome() + " | Disp: " + i.getQuantidadeDisponivel());
        }

        System.out.print("\nDigite o ID do Item: ");
        int idEscolhido = leitor.nextInt();
        Item item = ListaItems.buscarPorId(idEscolhido);
        if (item == null) { System.out.println("Item não encontrado."); return; }

        System.out.print("Quantidade desejada: ");
        int qtd = leitor.nextInt();



        //  ~ REGRA DE NEGÓCIO: Validação de Estoque
        if (qtd <= 0 || qtd > item.getQuantidadeDisponivel()) {
            System.out.println("\n[ERRO] Quantidade inválida ou estoque insuficiente!\n");
            return;
        }



        // Cria e salva requerimento
        Requerimento req = new Requerimento(sessao.getUsuarioLogado(), item, qtd);
        if (ListaRequerimentos.salvar(req)) {
            System.out.println("\nRequerimento #" + req.getIdRequerimento() + " enviado com sucesso!\n");
        } 
        else {
            System.out.println("\nFalha ao salvar no banco.\n");
        }
    } 
    catch (Exception e) {
        System.out.println("Erro ao processar requisição: " + e.getMessage());
    }
  }












  //  ~ Validar Requerimentoz
  private static void validarRequerimentos(Scanner leitor) {
    try {
        Sessao sessao = Sessao.get();
        if (!sessao.estaLogado() || !sessao.usuarioEhAdmin()) {
            System.out.println("\n[ERRO] Acesso restrito a administradores.\n");
            return;
        }

        List<Requerimento> pendentes = ListaRequerimentos.buscarPendentes();
        if (pendentes.isEmpty()) {
            System.out.println("\nNenhum requerimento pendente.\n");
            return;
        }

        System.out.println("\n====== Requerimentos Pendentes ======");
        for (Requerimento r : pendentes) {
            System.out.println("Req #" + r.getIdRequerimento() + " | Item: " + 
                (r.getItem() != null ? r.getItem().getNome() : "N/A") + 
                " | Qtd: " + r.getQuantidadeSolicitada());
        }

        System.out.print("\nDigite o ID para APROVAR (ou 0 para sair): ");
        int idReq = leitor.nextInt();
        if (idReq == 0) return;

        Requerimento alvo = pendentes.stream()
            .filter(r -> r.getIdRequerimento().equals(idReq))
            .findFirst().orElse(null);

        if (alvo != null) {
            alvo.setStatus("aprovado");
            alvo.setAdminAvaliador(sessao.getUsuarioLogado());
            
            // REGRA DE NEGÓCIO: Baixa no estoque ao aprovar
            Item item = alvo.getItem();
            if (item != null) {
                item.diminuirQuant(alvo.getQuantidadeSolicitada());
                ListaItems.atualizar(item);
            }

            ListaRequerimentos.atualizarStatus(alvo);
            System.out.println("\n✅ Requerimento aprovado e estoque atualizado!\n");
        } else {
            System.out.println("\n❌ ID não encontrado.\n");
        }
    } catch (Exception e) {
        System.out.println("Erro na validação: " + e.getMessage());
    }
  }

}
