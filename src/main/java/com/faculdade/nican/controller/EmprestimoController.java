package com.faculdade.nican.controller;

import com.faculdade.nican.model.entity.Emprestimo;
import com.faculdade.nican.model.service.EmprestimoService;
import java.util.List;

public class EmprestimoController {
    private final EmprestimoService emprestimoService = new EmprestimoService();

    public List<Emprestimo> buscarEmAberto() {
        return emprestimoService.buscarEmAberto();
    }

    public List<Emprestimo> buscarPorUsuario(Integer idUsuario) {
        return emprestimoService.buscarPorUsuario(idUsuario);
    }

    public List<Emprestimo> buscarEmAbertoDoUsuario(Integer idUsuario) {
        return emprestimoService.buscarEmAbertoDoUsuario(idUsuario);
    }

    public Emprestimo buscarPorId(Integer id) {
        return emprestimoService.buscarPorId(id);
    }

    public boolean registrarDevolucao(Emprestimo emprestimo, String estadoItem, String observacao) {
        return emprestimoService.registrarDevolucao(emprestimo, estadoItem, observacao);
    }
}
