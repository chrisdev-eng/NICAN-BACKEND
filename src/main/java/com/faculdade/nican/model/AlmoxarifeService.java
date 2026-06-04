package com.faculdade.nican.model;


import java.util.List;

public class AlmoxarifeService {

    private AlmoxarifeService() {}

    // lista todos os itens
    public static List<Item> listarTodos() {
        return ItemRepository.getListaItems();
    }

    // remove um item pelo ID
    // CORREÇÃO: agora verifica o retorno de ItemRepository.removerItem()
    // Antes: removerItem() era void → service sempre retornava null (sucesso) mesmo quando falhava
    // Agora: removerItem() retorna boolean → service propaga o erro corretamente para a View
    public static String removerItem(int idItem) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem remover itens.";
        }

        Item item = ItemRepository.buscarPorId(idItem);
        if (item == null) {
            return "Item não encontrado.";
        }

        boolean removido = ItemRepository.removerItem(item);
        if (!removido) {
            return "Não é possível excluir: este item possui requerimentos vinculados no histórico.";
        }

        return null; // null = sucesso
    }

    // adiciona um novo item
    public static String adicionarItem(String nome, int quantidade, Qualidade qualidade, Categoria categoria) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem adicionar itens.";
        }
        if (nome == null || nome.isBlank()) return "Nome do item é obrigatório.";
        if (quantidade <= 0) return "A quantidade deve ser maior que zero.";
        if (qualidade == null) return "Selecione o estado de conservação.";
        if (categoria == null) return "Selecione a categoria.";

        Admin admin = Sessao.get().getAdminLogado();
        Item novoItem = new Item(nome.trim(), quantidade, quantidade, qualidade, categoria, admin);
        ItemRepository.adicionarItem(novoItem);
        return null; // sucesso
    }

    // helpers para popular JComboBox na View
    public static String[] getCategorias() {
        return java.util.Arrays.stream(Categoria.values())
                .map(Categoria::getCategoria)
                .toArray(String[]::new);
    }

    public static String[] getQualidades() {
        return java.util.Arrays.stream(Qualidade.values())
                .map(Qualidade::getEstado)
                .toArray(String[]::new);
    }

    public static Categoria getCategoriaByLabel(String label) {
        for (Categoria c : Categoria.values()) {
            if (c.getCategoria().equals(label)) return c;
        }
        return null;
    }

    public static Qualidade getQualidadeByLabel(String label) {
        for (Qualidade q : Qualidade.values()) {
            if (q.getEstado().equals(label)) return q;
        }
        return null;
    }

    // NOVO: remove apenas uma quantidade do estoque
    public static String removerQuantidade(int idItem, int qtd) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem remover quantidades.";
        }
        return ItemRepository.removerQuantidade(idItem, qtd);
    }

    public static String adicionarQuantidade(int idItem, int qtd) {
        if (!Sessao.get().usuarioEhAdmin()) {
            return "Apenas administradores podem adicionar quantidades.";
        }
        return ItemRepository.adicionarQuantidade(idItem, qtd);
    }
}
