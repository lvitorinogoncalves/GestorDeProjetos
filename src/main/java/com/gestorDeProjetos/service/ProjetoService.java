package com.gestorDeProjetos.service;

import com.gestorDeProjetos.dto.ProjetoDTO;
import com.gestorDeProjetos.exceptions.ProjetoNotFoundException;

import java.util.List;

public interface ProjetoService {
    List<ProjetoDTO> listarProjetos();
    ProjetoDTO findById(Long id) throws ProjetoNotFoundException;
    ProjetoDTO criarProjeto(ProjetoDTO projetoDTO);
    ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO) throws ProjetoNotFoundException;
    boolean excluirProjeto(Long id) throws ProjetoNotFoundException;
}
