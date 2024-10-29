package com.gestorDeProjetos.repository;

import com.gestorDeProjetos.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {

    List<Membro> findByPessoaFuncionario(boolean funcionario);
}
