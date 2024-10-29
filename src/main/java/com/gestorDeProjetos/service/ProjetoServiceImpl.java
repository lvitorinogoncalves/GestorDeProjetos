package com.gestorDeProjetos.service;

import com.gestorDeProjetos.dto.ProjetoDTO;
import com.gestorDeProjetos.exceptions.ProjetoNotFoundException;
import com.gestorDeProjetos.mapper.ProjetoMapper;
import com.gestorDeProjetos.model.Projeto;
import com.gestorDeProjetos.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoServiceImpl implements ProjetoService {
    private final ProjetoRepository projetoRepository;
    private final ProjetoMapper projetoMapper;

    @Autowired
    public ProjetoServiceImpl(ProjetoRepository projetoRepository, ProjetoMapper projetoMapper) {
        this.projetoRepository = projetoRepository;
        this.projetoMapper = projetoMapper;
    }

    @Override
    public List<ProjetoDTO> listarProjetos() {
        List<Projeto> projetos = projetoRepository.findAll();
        return projetoMapper.toDTOs(projetos);
    }

    @Override
    public ProjetoDTO findById(Long id) throws ProjetoNotFoundException {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ProjetoNotFoundException("Projeto não encontrado com ID: " + id));

        return projetoMapper.toDTO(projeto);
    }

    public ProjetoDTO criarProjeto(ProjetoDTO projetoDTO) {
        Projeto projeto = projetoMapper.toEntity(projetoDTO);
        Projeto projetoSalvo = projetoRepository.save(projeto);
        return projetoMapper.toDTO(projetoSalvo);
    }

    public ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoAtualizadoDTO) {
        Projeto projetoExistente = validateAndGetProjeto(id);

        projetoMapper.updateEntityFromDTO(projetoAtualizadoDTO, projetoExistente);

        Projeto projetoSalvo = projetoRepository.save(projetoExistente);

        return projetoMapper.toDTO(projetoSalvo);
    }

    private Projeto validateAndGetProjeto(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ProjetoNotFoundException("Projeto com ID " + id + " não encontrado"));
    }

    public boolean excluirProjeto(Long id) {
        Projeto projeto = validateAndGetProjeto(id);

        if (projeto.getStatus().equalsIgnoreCase("iniciado") ||
                projeto.getStatus().equalsIgnoreCase("em_andamento") ||
                projeto.getStatus().equalsIgnoreCase("encerrado")) {
            return false;
        }

        projetoRepository.delete(projeto);
        return true;
    }

}
