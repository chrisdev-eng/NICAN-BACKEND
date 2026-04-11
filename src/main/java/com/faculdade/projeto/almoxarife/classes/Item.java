package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.login.classes.Admin;

import jakarta.persistence.*;
import java.time.LocalDateTime;




/*
 *  ~ Entidade do Item ~
 *
 *  Representa a tabela "almoxarifado" no banco de dados.
 *
 *  CORREÇÕES APLICADAS:
 *    - @Column(name = "quantidadeDisponivel") — corrigido; o SQL antigo usava "quantidadeDisp"
 *      mas o migration corrigido ja usa "quantidadeDisponivel"
 *    - @JoinColumn(name = "idAdmin_fk") — corrigido para bater com o banco
 *    - adminResponsavel é Admin (nao Usuario)
 */
@Entity
@Table(name = "almoxarifado")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idItem")
  private Integer idItem;

  @Column(name = "nome", nullable = false)
  private String nomeItem;

  @Column(name = "categoria", nullable = false)
  private String categoriaItem;

  @Column(name = "quantidadeTotal", nullable = false)
  private int quantidadeTotal;

  // CORRECAO: nome da coluna alinhado com o SQL corrigido
  @Column(name = "quantidadeDisponivel", nullable = false)
  private int quantidadeDisponivel;

  @Column(name = "qualidade", nullable = false)
  private String qualidadeItem;

  // CORRECAO: @JoinColumn com nome correto "idAdmin_fk" igual ao banco
  @ManyToOne
  @JoinColumn(name = "idAdmin_fk", nullable = false)
  private Admin adminResponsavel;

  @Column(name = "criadoEm")
  private LocalDateTime criadoEm = LocalDateTime.now();

  @Column(name = "atualizadoEm")
  private LocalDateTime atualizadoEm = LocalDateTime.now();



  // Constructor vazio obrigatorio para o JPA/Hibernate
  public Item() {}



  // Constructor principal usado pelo sistema
  public Item(String nome, int qtdTotal, int qtdDisponivel, Qualidade qualidade, Categoria categoria, Admin responsavel) {
    this.nomeItem = nome;
    this.quantidadeTotal = qtdTotal;
    this.quantidadeDisponivel = qtdDisponivel;
    this.qualidadeItem = qualidade.getEstado();
    this.categoriaItem = categoria.getCategoria();
    this.adminResponsavel = responsavel;
    this.criadoEm = LocalDateTime.now();
    this.atualizadoEm = LocalDateTime.now();
  }



  // GETTERS
  public int     getIdItem()               { return this.idItem; }
  public String  getNome()                 { return this.nomeItem; }
  public String  getQualidade()            { return this.qualidadeItem; }
  public String  getCategoria()            { return this.categoriaItem; }
  public int     getQuantidadeTotal()      { return this.quantidadeTotal; }
  public int     getQuantidadeDisponivel() { return this.quantidadeDisponivel; }
  public Admin   getAdminResponsavel()     { return this.adminResponsavel; }
  public LocalDateTime getCriadoEm()       { return this.criadoEm; }
  public LocalDateTime getAtualizadoEm()   { return this.atualizadoEm; }



  // SETTERS
  public void setIdItem(Integer idItem)                  { this.idItem = idItem; }
  public void setNomeItem(String nomeItem)               { this.nomeItem = nomeItem; }
  public void mudarQualidade(String novaQualidade)       { this.qualidadeItem = novaQualidade; this.atualizadoEm = LocalDateTime.now(); }
  public void setCategoriaItem(String categoriaItem)     { this.categoriaItem = categoriaItem; }
  public void setAdminResponsavel(Admin adminResponsavel){ this.adminResponsavel = adminResponsavel; }
  public void setAtualizadoEm(LocalDateTime dt)          { this.atualizadoEm = dt; }



  // MÉTODOS DE NEGÓCIO
  public void diminuirQuant(int quant) {
    // REGRA DE NEGÓCIO: nao permite quantidade negativa
    if (quant <= 0) return;
    if (this.quantidadeDisponivel >= quant) {
      this.quantidadeDisponivel -= quant;
      this.quantidadeTotal -= quant;
      this.atualizadoEm = LocalDateTime.now();
    }
  }

  public void aumentarQuant(int quant) {
    if (quant <= 0) return;
    this.quantidadeDisponivel += quant;
    this.quantidadeTotal += quant;
    this.atualizadoEm = LocalDateTime.now();
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
