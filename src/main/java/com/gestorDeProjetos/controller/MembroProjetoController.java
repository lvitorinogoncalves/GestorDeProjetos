package com.gestorDeProjetos.controller;

import com.gestorDeProjetos.dto.MembroProjetoDTO;
import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.response.Response;
import com.gestorDeProjetos.service.MembroProjetoService;
import com.gestorDeProjetos.service.MembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/projetos/{projetoId}")
@Tag(name = "MembroProjeto", description = "Gerenciamento de membros em projetos")
public class MembroProjetoController {

    @Autowired
    private MembroProjetoService membroProjetoService;

    @Autowired
    private MembroService membroService;

    @Operation(summary = "Adicionar membro ao projeto", description = "Adiciona membros específicos a um projeto existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Membro adicionado com sucesso ao projeto"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao adicionar membro")
    })
    @PostMapping("/adicionarMembro")
    public ResponseEntity<Response<List<MembroProjetoDTO>>> adicionarMembroAoProjeto(
            @PathVariable Long projetoId,
            @RequestBody List<Long> membrosIds) {

        List<Long> membrosNaoFuncionarios = membrosIds.stream()
                .filter(membroId -> !membroService.ehFuncionario(membroId))
                .toList();

        if (!membrosNaoFuncionarios.isEmpty()) {
            String mensagemErro = "Os seguintes membros não possuem atribuição de funcionário: " + membrosNaoFuncionarios;
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response<>(mensagemErro, null));
        }

        List<MembroProjetoDTO> membrosAdicionados = membroProjetoService.associarMembros(projetoId, membrosIds);

        return membrosAdicionados.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new Response<>("Nenhum membro foi adicionado ao projeto.", null))
                : ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>("Membros adicionados com sucesso.", membrosAdicionados));
    }
}
