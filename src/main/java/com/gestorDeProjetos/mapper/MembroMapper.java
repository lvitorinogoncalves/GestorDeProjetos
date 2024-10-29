package com.gestorDeProjetos.mapper;

import com.gestorDeProjetos.dto.MembroDTO;
import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.model.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class MembroMapper {

    public Membro toEntity(MembroDTO membroDTO) {
        Membro membro = new Membro();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(membroDTO.getNome());
        pessoa.setFuncionario(Boolean.TRUE);
        pessoa.setGerente(Boolean.FALSE);
        membro.setPessoa(pessoa);
        membro.setAtribuicao(membroDTO.getAtribuicao());
        return membro;
    }

    public MembroDTO toDTO(Membro membro) {
        MembroDTO membroDTO = new MembroDTO();
        membroDTO.setNome(membro.getPessoa().getNome());
        membroDTO.setAtribuicao(membro.getAtribuicao());
        return membroDTO;
    }
}
