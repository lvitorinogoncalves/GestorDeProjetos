package com.gestorDePorjetos.controller;

import com.gestorDePorjetos.dto.MembroDTO;
import com.gestorDePorjetos.model.Membro;
import com.gestorDePorjetos.service.MembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membros")
public class MembroController {

    @Autowired
    private MembroService membroService;

    @Operation(summary = "Adicionar novo membro", description = "Este endpoint recebe um MembroDTO e retorna o novo membro adicionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membro adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping("/adicionar")
    public Membro adicionarMembro(@RequestBody MembroDTO membroDTO) {
        return membroService.adicionarMembro(membroDTO);
    }
}
