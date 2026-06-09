package com.faculdade.nican.model.service;

import com.faculdade.nican.model.entity.*; import com.faculdade.nican.model.service.*; import com.faculdade.nican.model.repository.*;
import com.faculdade.nican.model.repository.*;
import java.util.List;

/**
 * Service responsavel pelas regras e operacoes de Requerimento.
 */
public class RequerimentoService {

    public static boolean criarRequerimento(Usuario usuario, Item item, Integer quantidadeSolicitada) {
        Requerimento requerimento = new Requerimento(usuario, item, quantidadeSolicitada);
        return RequerimentoRepository.salvar(requerimento);
    }

    public static boolean salvar(Requerimento requerimento) {
        return RequerimentoRepository.salvar(requerimento);
    }

    public static List<Requerimento> buscarPendentes() {
        return RequerimentoRepository.buscarPendentes();
    }

    public static List<Requerimento> listarPorUsuario(Integer idUsuario) {
        return RequerimentoRepository.listarPorUsuario(idUsuario);
    }

    public static boolean atualizarStatus(Requerimento requerimento) {
        return RequerimentoRepository.atualizarStatus(requerimento);
    }

    public static boolean aprovar(Requerimento requerimento, Admin admin) {
        return RequerimentoRepository.aprovar(requerimento, admin);
    }

    public static boolean recusar(Requerimento requerimento, Admin admin) {
        return RequerimentoRepository.recusar(requerimento, admin);
    }
}
