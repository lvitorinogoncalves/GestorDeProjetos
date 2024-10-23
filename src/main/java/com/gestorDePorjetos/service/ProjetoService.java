package com.gestorDePorjetos.service;

import com.gestorDePorjetos.repository.ProjetoRepository;
import com.gestorDePorjetos.model.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto atualizarProjeto(Long id, Projeto projetoAtualizado) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));

        projeto.setNome(projetoAtualizado.getNome());
        projeto.setDataInicio(projetoAtualizado.getDataInicio());
        projeto.setDataPrevisaoFim(projetoAtualizado.getDataPrevisaoFim());
        projeto.setDataFim(projetoAtualizado.getDataFim());
        projeto.setOrcamento(projetoAtualizado.getOrcamento());
        projeto.setDescricao(projetoAtualizado.getDescricao());
        projeto.setStatus(projetoAtualizado.getStatus());
        projeto.setRisco(projetoAtualizado.getRisco());

        return projetoRepository.save(projeto);
    }

    public boolean excluirProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));

        if (projeto.getStatus().equals("iniciado") || projeto.getStatus().equals("em_andamento") || projeto.getStatus().equals("encerrado")) {
            return false;
        }

        projetoRepository.delete(projeto);
        return true;
    }
}
