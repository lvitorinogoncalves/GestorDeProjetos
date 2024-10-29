package com.gestorDeProjetos.repository;

import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.model.MembroProjeto;
import com.gestorDeProjetos.model.MembroProjetoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembroProjetoRepository extends JpaRepository<MembroProjeto, MembroProjetoId> {
    List<Membro> findMembrosByProjetoId(Long projetoId);
}
