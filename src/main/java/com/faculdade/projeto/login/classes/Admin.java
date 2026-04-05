package com.faculdade.projeto.login.classes;


import org.hibernate.annotations.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;



/*
 *  Classe Admin do Sistema.
 *
 *  A classe admin criamos pra poder relacionar com a classe usuario, pois somentes admins podem criar um usuario
 *  e um usuario recebe o id do admin que criou a sua conta...
 *  Ela segue a mesma estrutura da classe Usuario, com pequenas alterationz.
 *  Bom.. e por ser uma classe do tipo admin, logo, nao sera preciso de uma fk_admin (ate pq vai linkar com quem 
 *  kkkkk)
 */
@Entity
@Table(name = "admin")
public class Admin {


  //  ~ Definimos que a variavel vai ser um do tipo de um ID, 
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)     //  ~ Diz que e do tipo serial (auto incremento de id)
  @Column(name = "idUsuario")                             //  ~ Nome da Coluna que vai se relacionar      
  private Integer id;
  
  @Column(name = "nome", nullable = false, length = 100)  //  ~ Variavel do tipo Varchar, que n pode ser null. 
  private String nome;

  @Column(name = "login", nullable = false, length = 100, unique = true)   //  ~ Mudanca de Email -> Login 
  private String login;
  
  @Column(name = "senha", nullable = false, length = 255)
  private String senha;


  @CreationTimestamp
  @Column(name = "criadoEm", updatable = false)
  private LocalDateTime criadoEm;

  @CreationTimestamp
  @Column(name = "atualizadoEm")
  private LocalDateTime atualizadoEm;




}
