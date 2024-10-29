package com.gestorDeProjetos.service;

import com.gestorDeProjetos.dto.MembroDTO;
import com.gestorDeProjetos.model.Membro;

import java.util.List;

public interface MembroService {
    MembroDTO adicionarMembro(MembroDTO membroDTO);
    Membro save(Membro membro);
    List<Membro> findAll();
    Membro findById(Long id);
    void delete(Long id);

    boolean ehFuncionario(Long idMembro);
}
