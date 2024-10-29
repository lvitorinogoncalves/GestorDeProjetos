package com.gestorDeProjetos.controller;

import com.gestorDeProjetos.model.Pessoa;
import com.gestorDeProjetos.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pessoas")
@Tag(name = "Pessoa", description = "Gerenciamento de pessoas no sistema")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Listar todas as pessoas", description = "Retorna uma lista com todas as pessoas cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Pessoa>> getAllPessoas() {
        List<Pessoa> pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @Operation(summary = "Buscar pessoa por ID", description = "Retorna uma pessoa específica com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada e retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.findById(id);
        return ResponseEntity.ok(pessoa);
    }

    @Operation(summary = "Criar nova pessoa", description = "Adiciona uma nova pessoa ao sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {
        Pessoa savedPessoa = pessoaService.save(pessoa);
        return ResponseEntity.status(201).body(savedPessoa);
    }

    @Operation(summary = "Atualizar pessoa", description = "Atualiza os dados de uma pessoa com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        pessoa.setId(id);
        Pessoa updatedPessoa = pessoaService.save(pessoa);
        return ResponseEntity.ok(updatedPessoa);
    }

    @Operation(summary = "Deletar pessoa", description = "Remove uma pessoa do sistema com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
