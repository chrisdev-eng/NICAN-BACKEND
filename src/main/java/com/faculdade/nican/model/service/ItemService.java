package com.faculdade.nican.model.service;

import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.entity.Categoria;
import com.faculdade.nican.model.entity.Item;
import com.faculdade.nican.model.entity.Qualidade;
import com.faculdade.nican.model.entity.Sessao;
import com.faculdade.nican.model.repository.ItemRepository;
import java.util.Arrays;
import java.util.List;

public class ItemService {

    public List<Item> listarTodos() {
        return ItemRepository.getListaItems();
    }

    public Item buscarPorId(Integer idItem) {
        return ItemRepository.buscarPorId(idItem);
    }

    public String adicionarItem(String nome, int quantidade, Qualidade qualidade, Categoria categoria) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem adicionar itens.";
        }
        if (nome == null || nome.isBlank()) return "Nome do item e obrigatorio.";
        if (quantidade <= 0) return "A quantidade deve ser maior que zero.";
        if (qualidade == null) return "Selecione o estado de conservacao.";
        if (categoria == null) return "Selecione a categoria.";

        Admin admin = Sessao.get().getAdminLogado();
        Item novoItem = new Item(nome.trim(), quantidade, quantidade, qualidade, categoria, admin);
        ItemRepository.adicionarItem(novoItem);
        return null;
    }

    public String removerItem(int idItem) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem remover itens.";
        }

        Item item = ItemRepository.buscarPorId(idItem);
        if (item == null) {
            return "Item nao encontrado.";
        }

        boolean removido = ItemRepository.removerItem(item);
        if (!removido) {
            return "Nao e possivel excluir: este item possui requerimentos vinculados no historico.";
        }

        return null;
    }

    public String removerQuantidade(int idItem, int quantidade) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem remover quantidades.";
        }
        return ItemRepository.removerQuantidade(idItem, quantidade);
    }

    public String adicionarQuantidade(int idItem, int quantidade) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem adicionar quantidades.";
        }
        return ItemRepository.adicionarQuantidade(idItem, quantidade);
    }

    public String[] getCategorias() {
        return Arrays.stream(Categoria.values())
                .map(Categoria::getCategoria)
                .toArray(String[]::new);
    }

    public String[] getQualidades() {
        return Arrays.stream(Qualidade.values())
                .map(Qualidade::getEstado)
                .toArray(String[]::new);
    }

    public Categoria getCategoriaByLabel(String label) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.getCategoria().equals(label)) return categoria;
        }
        return null;
    }

    public Qualidade getQualidadeByLabel(String label) {
        for (Qualidade qualidade : Qualidade.values()) {
            if (qualidade.getEstado().equals(label)) return qualidade;
        }
        return null;
    }
}
