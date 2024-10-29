package com.gestorDeProjetos.service;

import com.gestorDeProjetos.dto.MembroProjetoDTO;
import com.gestorDeProjetos.model.*;
import com.gestorDeProjetos.repository.MembroProjetoRepository;
import com.gestorDeProjetos.repository.MembroRepository;
import com.gestorDeProjetos.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembroProjetoServiceImpl implements MembroProjetoService {

    @Autowired
    private MembroProjetoRepository membroProjetoRepository;
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private MembroRepository membroRepository;

    @Override
    public List<MembroProjetoDTO> associarMembros(Long projetoId, List<Long> membrosIds) {
        var projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado: " + projetoId));

        return membrosIds.stream()
                .map(idMembro -> {
                    var membro = membroRepository.findById(idMembro)
                            .orElseThrow(() -> new IllegalArgumentException("Membro não encontrado: " + idMembro));

                    var membroProjetoId = new MembroProjetoId(idMembro, projetoId);
                    var membroProjeto = new MembroProjeto();
                    membroProjeto.setId(membroProjetoId);
                    membroProjeto.setMembro(membro);
                    membroProjeto.setProjeto(projeto);

                    membroProjetoRepository.save(membroProjeto);

                    return new MembroProjetoDTO(idMembro, projetoId);
                })
                .collect(Collectors.toList());
    }

    public List<Membro> getMembrosNaoAssociadosAoProjeto(Long projetoId) {
        List<Membro> todosMembros = membroRepository.findAll();
        List<Membro> membrosAssociados = membroProjetoRepository.findMembrosByProjetoId(projetoId);

        return todosMembros.stream()
                .filter(membro -> !membrosAssociados.contains(membro))
                .collect(Collectors.toList());
    }

    public List<Membro> getMembrosAssociadosAoProjeto(Long projetoId) {
        return membroProjetoRepository.findMembrosByProjetoId(projetoId);
    }
}
