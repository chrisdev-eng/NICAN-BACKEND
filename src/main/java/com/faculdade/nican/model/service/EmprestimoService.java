package com.faculdade.nican.model.service;

import com.faculdade.nican.model.entity.Emprestimo;
import com.faculdade.nican.model.repository.EmprestimoRepository;
import java.util.List;

public class EmprestimoService {

    public List<Emprestimo> buscarEmAberto() {
        return EmprestimoRepository.buscarEmAberto();
    }

    public List<Emprestimo> buscarPorUsuario(Integer idUsuario) {
        return EmprestimoRepository.buscarPorUsuario(idUsuario);
    }

    public List<Emprestimo> buscarEmAbertoDoUsuario(Integer idUsuario) {
        return EmprestimoRepository.buscarEmAbertoDoUsuario(idUsuario);
    }

    public Emprestimo buscarPorId(Integer id) {
        return EmprestimoRepository.buscarPorId(id);
    }

    public boolean registrarDevolucao(Emprestimo emprestimo, String estadoItem, String observacao) {
        return EmprestimoRepository.registrarDevolucao(emprestimo, estadoItem, observacao);
    }
}
