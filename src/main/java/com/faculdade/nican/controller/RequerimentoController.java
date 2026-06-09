package com.faculdade.nican.controller;

import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.entity.Item;
import com.faculdade.nican.model.entity.Requerimento;
import com.faculdade.nican.model.service.RequerimentoService;
import com.faculdade.nican.model.entity.Usuario;
import java.util.List;

public class RequerimentoController {

    public boolean criarRequerimento(Usuario usuario, Item item, Integer quantidadeSolicitada) {
        return RequerimentoService.criarRequerimento(usuario, item, quantidadeSolicitada);
    }

    public List<Requerimento> buscarPendentes() {
        return RequerimentoService.buscarPendentes();
    }

    public List<Requerimento> listarPorUsuario(Integer idUsuario) {
        return RequerimentoService.listarPorUsuario(idUsuario);
    }

    public boolean aprovar(Requerimento requerimento, Admin admin) {
        return RequerimentoService.aprovar(requerimento, admin);
    }

    public boolean recusar(Requerimento requerimento, Admin admin) {
        return RequerimentoService.recusar(requerimento, admin);
    }
}
