package com.gestorDePorjetos.controller;

import com.gestorDePorjetos.model.Projeto;
import com.gestorDePorjetos.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Operation(summary = "Listar todos os projetos", description = "Este endpoint retorna a lista completa de projetos cadastrados")
    @GetMapping
    public List<Projeto> listarProjetos() {
        return projetoService.listarProjetos();
    }

    @Operation(summary = "Criar um novo projeto", description = "Cria um novo projeto no sistema")
    @PostMapping
    public Projeto criarProjeto(@RequestBody Projeto projeto) {
        return projetoService.criarProjeto(projeto);
    }

    @Operation(summary = "Atualizar um projeto", description = "Atualiza um projeto existente")
    @PutMapping("/{id}")
    public Projeto atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        return projetoService.atualizarProjeto(id, projeto);
    }

    @Operation(summary = "Excluir um projeto", description = "Exclui um projeto caso ele não esteja em andamento, iniciado ou encerrado")
    @ApiResponse(responseCode = "200", description = "Projeto excluído com sucesso")
    @ApiResponse(responseCode = "403", description = "A exclusão não é permitida para projetos em andamento, iniciados ou encerrados")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirProjeto(@PathVariable Long id) {
        boolean deletado = projetoService.excluirProjeto(id);
        if (deletado) {
            return ResponseEntity.ok("Projeto excluído com sucesso.");
        }
        return ResponseEntity.status(403).body("Não é possível excluir este projeto.");
    }
}
