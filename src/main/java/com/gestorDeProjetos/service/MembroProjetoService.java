package com.gestorDeProjetos.service;

import com.gestorDeProjetos.dto.MembroProjetoDTO;
import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.response.Response;

import java.util.List;

public interface MembroProjetoService {

    List<MembroProjetoDTO> associarMembros(Long projetoId, List<Long> membrosIds);

    public List<Membro> getMembrosNaoAssociadosAoProjeto(Long projetoId);

    public List<Membro> getMembrosAssociadosAoProjeto(Long projetoId);
}
