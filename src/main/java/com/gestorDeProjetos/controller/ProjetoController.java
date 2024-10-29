package com.gestorDeProjetos.controller;

import com.gestorDeProjetos.dto.ProjetoDTO;
import com.gestorDeProjetos.exceptions.ErrorResponse;
import com.gestorDeProjetos.exceptions.ProjetoNotFoundException;
import com.gestorDeProjetos.model.Pessoa;
import com.gestorDeProjetos.service.PessoaService;
import com.gestorDeProjetos.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/projetos")
@Tag(name = "Projeto", description = "Gerenciamento de projetos no sistema")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Listar todos os projetos", description = "Retorna uma lista com todos os projetos cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de projetos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarProjetos() {
        List<ProjetoDTO> projetos = projetoService.listarProjetos();
        return ResponseEntity.ok(projetos);
    }

    @Operation(summary = "Buscar projeto por ID", description = "Retorna um projeto específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto encontrado e retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> getProjetoById(@PathVariable Long id) {
        ProjetoDTO projeto = projetoService.findById(id);
        return ResponseEntity.ok(projeto);
    }

    @Operation(summary = "Criar novo projeto", description = "Adiciona um novo projeto ao sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping("/salvar")
    public ResponseEntity<ProjetoDTO> criarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        validaGerente(projetoDTO);

        ProjetoDTO savedProjeto = projetoService.criarProjeto(projetoDTO);
        return ResponseEntity.status(201).body(savedProjeto);
    }

    private void validaGerente(ProjetoDTO projetoDTO) {
        Pessoa gerente = pessoaService.findById(projetoDTO.getIdGerente());

        if (gerente == null) {
            throw new IllegalArgumentException("Gerente não encontrado.");
        }
    }

    @Operation(summary = "Atualizar projeto", description = "Atualiza os dados de um projeto com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
        try {
            ProjetoDTO projetoAtualizado = projetoService.atualizarProjeto(id, projetoDTO);
            return ResponseEntity.ok(projetoAtualizado);
        } catch (ProjetoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Excluir projeto", description = "Remove um projeto do sistema com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirProjeto(@PathVariable Long id) {
        try {
            boolean isDeleted = projetoService.excluirProjeto(id);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Projeto não encontrado"));
            }
            return ResponseEntity.ok("Projeto excluído com sucesso.");
        } catch (ProjetoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }
}
