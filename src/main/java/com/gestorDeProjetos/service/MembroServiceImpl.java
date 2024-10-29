package com.gestorDeProjetos.service;

import com.gestorDeProjetos.dto.MembroDTO;
import com.gestorDeProjetos.mapper.MembroMapper;
import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.model.Pessoa;
import com.gestorDeProjetos.repository.MembroRepository;
import com.gestorDeProjetos.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembroServiceImpl implements MembroService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MembroRepository membroRepository;

    @Autowired
    private MembroMapper membroMapper;

    public MembroDTO adicionarMembro(MembroDTO membroDTO) {
        validarMembro(membroDTO);
        Membro membro = membroMapper.toEntity(membroDTO);
        Pessoa pessoaSalvo = pessoaRepository.save(membro.getPessoa());
        membro.setPessoa(pessoaSalvo);
        Membro membroSalvo = membroRepository.save(membro);
        return membroMapper.toDTO(membroSalvo);
    }

    private void validarMembro(MembroDTO membroDTO) {
        if (membroDTO.getNome() == null || membroDTO.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do membro não pode ser vazio.");
        }
    }

    @Override
    public Membro save(Membro membro) {
        return membroRepository.save(membro);
    }

    @Override
    public List<Membro> findAll() {
        return membroRepository.findAll();
    }

    @Override
    public Membro findById(Long id) {
        return membroRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        membroRepository.deleteById(id);
    }

    @Override
    public boolean ehFuncionario(Long idMembro) {
        Membro membro = findById(idMembro);
        return membro != null && membro.getPessoa().getFuncionario();
    }
}
