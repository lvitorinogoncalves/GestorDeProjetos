package com.gestorDeProjetos.service;

import com.gestorDeProjetos.model.Pessoa;
import com.gestorDeProjetos.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Override
    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        pessoaRepository.deleteById(id);
    }
}
