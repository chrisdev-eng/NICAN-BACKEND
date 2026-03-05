package com.faculdade.projeto.almoxarife.classes;





public class Item {
  

  private int idItem;
  private String nomeItem;
  private int quantidadeItem;
  private String ramoSecaoItem;
  //  ~ Ver o Qualidade.java para entender; Basicamente serve para definir a qualidade do item
  private String qualidadeItem;
  //  ~ Ver o Categoria.java
  private String categoriaItem;





  //  ~ Construtor 1, caso nao seja definido de qual secao/ramo e o item, logo
  //  ~ E um item de responsabilidade do Grupo
  public Item(  
    String nomeDefined, 
    int quantidadeDefined,
    Qualidade qualidadeDefined,
    Categoria categoriaDefined  
  ) {
    this.nomeItem = nomeDefined;
    this.quantidadeItem = quantidadeDefined;
    this.qualidadeItem = qualidadeDefined.getEstado();
    this.categoriaItem = categoriaDefined.getCategoria();
    
    this.ramoSecaoItem = "Grupo";
    this.idItem = ListaItems.getListaTamanho();
    ListaItems.adicionarItem(  this  );
  }
    
  //  ~ Se for passado um ramo/secao para o Constructor, logo o material e da responsabilidade
  //  ~ de tal secao informada...
  public Item(  
    String nomeDefined, 
    int quantidadeDefined,
    String ramoSecaoDefined,
    Qualidade qualidadeDefined,
    Categoria categoriaDefined  
  ) {
    this.nomeItem = nomeDefined;
    this.quantidadeItem = quantidadeDefined;
    this.qualidadeItem = qualidadeDefined.getEstado();
    this.categoriaItem = categoriaDefined.getCategoria();
    this.ramoSecaoItem = ramoSecaoDefined;
   
    this.idItem = ListaItems.getListaTamanho();
    ListaItems.adicionarItem(  this  );
  }





  //  ~ GETTERS ~ Passar/pegar as infos do Item Atual
  public int getIdItem()        {  return this.idItem;  }
  public String getNome()       {  return this.nomeItem;  }
  public String getRamoSecao()  {  return this.ramoSecaoItem;  }
  public String getQualiidade() {  return this.qualidadeItem;  }
  public String getCategoria()  {  return this.categoriaItem;  } 
  public int getQuantidade()    {  return this.quantidadeItem; }

  


  //  ~ SETTERS ~ Define novos valores
  public void mudarQualidade(  String novaQualidade  ) {  this.qualidadeItem = novaQualidade;  }
  public void diminuirQuant(  int quantParaDiminuir  ) {  this.quantidadeItem -= quantParaDiminuir;  }
  public void aumentarQuant(  int quantParaAumentar  ) {  this.quantidadeItem += quantParaAumentar;  }





  public void infosGeralItem() {
    System.out.println("\n~ " + getNome() + " ~\n");
    System.out.println("[  ID  ]: " + getIdItem());
    System.out.println("Quantidade: " + getQuantidade());
    System.out.println("Qualidade: " + getQualiidade());
    System.out.println("Categoria: " + getCategoria());
    System.out.println("Responsavel pelo Item [Ramo/Secao]: " + getRamoSecao());
    System.out.println("\n\n");
  }
}
