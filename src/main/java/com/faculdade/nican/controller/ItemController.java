package com.faculdade.nican.controller;

import com.faculdade.nican.model.entity.Categoria;
import com.faculdade.nican.model.entity.Item;
import com.faculdade.nican.model.entity.Qualidade;
import com.faculdade.nican.model.service.ItemService;
import java.util.List;

public class ItemController {
    private final ItemService itemService = new ItemService();

    public List<Item> listarTodos() {
        return itemService.listarTodos();
    }

    public String adicionarItem(String nome, int quantidade, Qualidade qualidade, Categoria categoria) {
        return itemService.adicionarItem(nome, quantidade, qualidade, categoria);
    }

    public String removerItem(int idItem) {
        return itemService.removerItem(idItem);
    }

    public String removerQuantidade(int idItem, int quantidade) {
        return itemService.removerQuantidade(idItem, quantidade);
    }

    public String adicionarQuantidade(int idItem, int quantidade) {
        return itemService.adicionarQuantidade(idItem, quantidade);
    }

    public String[] getCategorias() {
        return itemService.getCategorias();
    }

    public String[] getQualidades() {
        return itemService.getQualidades();
    }

    public Categoria getCategoriaByLabel(String label) {
        return itemService.getCategoriaByLabel(label);
    }

    public Qualidade getQualidadeByLabel(String label) {
        return itemService.getQualidadeByLabel(label);
    }
}
