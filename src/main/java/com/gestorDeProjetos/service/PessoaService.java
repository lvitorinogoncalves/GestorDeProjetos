package com.gestorDeProjetos.service;

import com.gestorDeProjetos.model.Pessoa;

import java.util.List;

public interface PessoaService {
    Pessoa save(Pessoa pessoa);
    List<Pessoa> findAll();
    Pessoa findById(Long id);
    void delete(Long id);
}
