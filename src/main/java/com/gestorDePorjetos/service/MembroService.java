package com.gestorDePorjetos.service;

import com.gestorDePorjetos.dto.MembroDTO;
import com.gestorDePorjetos.model.Membro;
import com.gestorDePorjetos.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;

    public Membro adicionarMembro(MembroDTO membroDTO) {
        Membro membro = new Membro();
        membro.setNome(membroDTO.getNome());
        membro.setAtribuicao(membroDTO.getAtribuicao());
        return membroRepository.save(membro);
    }
}
