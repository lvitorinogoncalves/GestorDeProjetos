package com.gestorDePorjetos.controller;

import com.gestorDePorjetos.dto.MembroDTO;
import com.gestorDePorjetos.model.Membro;
import com.gestorDePorjetos.service.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membros")
public class MembroController {

    @Autowired
    private MembroService membroService;

    @PostMapping("/adicionar")
    public Membro adicionarMembro(@RequestBody MembroDTO membroDTO) {
        return membroService.adicionarMembro(membroDTO);
    }
}
