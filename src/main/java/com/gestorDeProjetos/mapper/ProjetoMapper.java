package com.gestorDeProjetos.mapper;

import com.gestorDeProjetos.dto.ProjetoDTO;
import com.gestorDeProjetos.model.Projeto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjetoMapper {

    public ProjetoDTO toDTO(Projeto projeto) {
        if (projeto == null) {
            return new ProjetoDTO();
        }

        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setId(projeto.getId());
        projetoDTO.setNome(projeto.getNome());
        projetoDTO.setDataInicio(projeto.getDataInicio());
        projetoDTO.setDataPrevisaoFim(projeto.getDataPrevisaoFim());
        projetoDTO.setDataFim(projeto.getDataFim());
        projetoDTO.setDescricao(projeto.getDescricao());
        projetoDTO.setOrcamento(projeto.getOrcamento());
        projetoDTO.setStatus(projeto.getStatus());
        projetoDTO.setRisco(projeto.getRisco());
        projetoDTO.setIdGerente(projeto.getIdGerente().getId());

        return projetoDTO;
    }

    public Projeto toEntity(ProjetoDTO projetoDTO) {
        if (projetoDTO == null) {
            return null;
        }

        Projeto projeto = new Projeto();
        projeto.setNome(projetoDTO.getNome());
        projeto.setDataInicio(projetoDTO.getDataInicio());
        projeto.setDataPrevisaoFim(projetoDTO.getDataPrevisaoFim());
        projeto.setDataFim(projetoDTO.getDataFim());
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto.setOrcamento(projetoDTO.getOrcamento());
        projeto.setStatus(projetoDTO.getStatus());
        projeto.setRisco(projetoDTO.getRisco());
        projeto.setIdGerente(projetoDTO.getGerente());

        return projeto;
    }

    public List<ProjetoDTO> toDTOs(List<Projeto> projetos) {
        List<ProjetoDTO> projetoDTOs = new ArrayList<>();
        for (Projeto projeto : projetos) {
            projetoDTOs.add(toDTO(projeto));
        }
        return projetoDTOs;
    }

    public void updateEntityFromDTO(ProjetoDTO projetoDTO, Projeto projeto) {
        if (projetoDTO == null || projeto == null) {
            return;
        }

        projeto.setNome(projetoDTO.getNome());
        projeto.setDataInicio(projetoDTO.getDataInicio());
        projeto.setDataPrevisaoFim(projetoDTO.getDataPrevisaoFim());
        projeto.setDataFim(projetoDTO.getDataFim());
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto.setOrcamento(projetoDTO.getOrcamento());
        projeto.setStatus(projetoDTO.getStatus());
        projeto.setRisco(projetoDTO.getRisco());
    }
}
