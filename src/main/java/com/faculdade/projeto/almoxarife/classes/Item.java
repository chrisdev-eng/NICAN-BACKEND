package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.login.classes.Admin;
import com.faculdade.projeto.login.classes.Usuario;

import jakarta.persistence.*;
import java.time.LocalDateTime;







/*
 *  ~ Entidade do Item ~
 *
 *
 *  Aqui e onde inicializamos/demos a base para a entidade que se relaciona a tabela de itens do banco
 *
 */
@Entity
@Table(name = "almoxarifado")
public class Item {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)         //  ~ Tipo Serial de id, autoincrement 
  @Column(name = "idItem")
  private Integer idItem;
  
  @Column(name = "nome", nullable = false)
  private String nomeItem;

  @Column(name = "categoria", nullable = false)
  private String categoriaItem;
  



  @Column(name = "quantidadeTotal", nullable = false)
  private int quantidadeTotal;

  @Column(name = "quantidadeDisponivel", nullable = false)
  private int quantidadeDisponivel;
  
  @Column(name = "qualidade", nullable = false)
  private String qualidadeItem;



  //  ~ Relacionamento JPA, um admin e responsavel por varios itens...
  @ManyToOne
  @JoinColumn(name = "idAdmin", nullable = false)
  private Usuario adminResponsavel;



  @Column(name = "criadoEm")
  private LocalDateTime criadoEm = LocalDateTime.now();
    
  @Column(name = "atualizadoEm")
  private LocalDateTime atualizadoEm = LocalDateTime.now();



  //private String ramoSecaoItem;                 //  ~ Cagaram p isso no banco de dados :/ 
                                                  //  ~ Eu msm q esqueci de implementar KKK, comentei p dps arrumar






  //  ~ Um constructor vazio, para evitar erros de uso pelo JPA hibernate e talz...
  public Item() {}



  public Item(String nome, int qtdTotal, int qtdDisponivel, Qualidade qualidade, Categoria categoria, Usuario responsavel  ) {
      this.nomeItem = nome;
      this.quantidadeTotal = qtdTotal;
      this.quantidadeDisponivel = qtdDisponivel;
      this.qualidadeItem = qualidade.getEstado();
      this.categoriaItem = categoria.getCategoria();
      this.adminResponsavel = responsavel;
  }









  //  ~ GETTERS ~ Passar/pegar as infos do Item Atual
  public int getIdItem()        {  return this.idItem;  }
  public String getNome()       {  return this.nomeItem;  }
  public String getQualidade() {  return this.qualidadeItem;  }
  public String getCategoria()  {  return this.categoriaItem;  } 
  public int getQuantidadeTotal()    {  return this.quantidadeTotal;  }
  public int getQuantidadeDisponivel()    {  return this.quantidadeDisponivel;  }
  public Admin getAdminResponsavel() {  return adminResponsavel;  }
  //public String getRamoSecao()  {  return this.ramoSecaoItem;  }

  


  //  ~ SETTERS ~ Define novos valores
  public void setIdItem(  Integer idItem  ) {  this.idItem = idItem;  }
  public void setNomeItem(  String nomeItem  ) {  this.nomeItem = nomeItem;  }
  public void mudarQualidade(  String novaQualidade  ) {  this.qualidadeItem = novaQualidade;  }
  public void setCategoriaItem(String categoriaItem) {  this.categoriaItem = categoriaItem;  }
  public void setAdminResponsavel(Admin adminResponsavel) {  this.adminResponsavel = adminResponsavel;  }





  //  ~ Methodos's ~
  public void diminuirQuant(int quant) {
      if(this.quantidadeDisponivel >= quant) {
          this.quantidadeDisponivel -= quant;
          this.quantidadeTotal -= quant; // Lógica simplificada
      }
  }


  public void aumentarQuant(int quant) {
      this.quantidadeDisponivel += quant;
      this.quantidadeTotal += quant;
  }


  public void infosGeralItem() {
    System.out.println("\n~ " + getNome() + " ~\n");
    System.out.println("[  ID  ]: " + getIdItem());
    System.out.println("Quantidade Total: " + getQuantidadeTotal());
    System.out.println("Quantidade Disponivel: " + getQuantidadeDisponivel());
    System.out.println("Qualidade: " + getQualidade());
    System.out.println("Categoria: " + getCategoria());
    System.out.println("\n\n");
  }
}
