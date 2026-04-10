package com.faculdade.projeto.almoxarife.classes;

import com.faculdade.projeto.login.classes.Admin;
import com.faculdade.projeto.login.classes.Usuario;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;





/*
 *  ~ Entidade de Requerimento do Sistema ~
 *
 *  Aqui e onde ocorre o monitoramento dos requerimento no nosso sistema, no qual guarda as informacoes
 *  do usuario que fez o requerimento, adminresponsavel, o horario do requerimento e talz, alem de 
 *  um id unico pro requerimento, junto, obviamente, do item que esta sendo requerido e etc...
 *
 *
 * */
@Entity
@Table(name = "requerimento")
public class Requerimento {




  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)           //  ~ Id de tipo serial, autoincrement...
  private Integer idRequerimento;

  @ManyToOne                                                    //  ~ Diz q um usuario pode ter varios requerimentos
  @JoinColumn(name = "idUsuario", nullable = false)
  private Usuario usuario;

  @ManyToOne                                                    //  ~ Mesma coisa, um item pode ter muitos requerimentos.. 
  @JoinColumn(name = "idItem", nullable = false)
  private Item item;

  private Integer quantidadeSolicitada;                         //  ~ Quantidade inserida no sistema (aki)
  private String status;                                        //  ~ pendente, aprovado, recusado



  @ManyToOne                                                    //  ~ Novamente, muitos ade eme p um requerimento
  @JoinColumn(name = "idAdmin")
  private Usuario adminAvaliador;



  private LocalDate dataAprovacao;
  private LocalDate dataSolicitacao;   
  private LocalDateTime criadoEm;
  


  



  public Requerimento() {}


  public Requerimento(Usuario usuario, Item item, Integer qtd) {
      this.usuario = usuario;
      this.item = item;
      this.quantidadeSolicitada = qtd;
      this.status = "pendente";
      this.dataSolicitacao = LocalDate.now();
      this.criadoEm = LocalDateTime.now();

  }





  //  ~ GETTERS's ~
  public Integer getIdRequerimento()        {  return idRequerimento; }
  public Usuario getUsuario()               {  return usuario; }    
  public Item getItem()                     {  return item; }
  public Integer getQuantidadeSolicitada()  {  return quantidadeSolicitada; }    
  public String getStatus()                 {  return status; }
  public LocalDate getDataAprovacao()       {  return dataAprovacao;  }
  public LocalDate getDataSolicitacao()     {  return dataSolicitacao;  }
  public LocalDateTime getCriadoEm()        {  return criadoEm;  }



  //  ~ SETTER's ~
  public void setUsuario(Usuario usuario) { this.usuario = usuario; }
  public void setItem(Item item) { this.item = item; }
  public void setQuantidadeSolicitada(Integer qtd) { this.quantidadeSolicitada = qtd; }
  public void setStatus(String status) { this.status = status; }
  public void setAdminAvaliador(Usuario adminAvaliador) {this.adminAvaliador = adminAvaliador;}





  //  ~ Metodo's ~
  public void aprovar(Admin admin) {
    this.status = "aprovado";
    this.adminAvaliador = admin;
    this.dataAprovacao = LocalDate.now();
  }
}
