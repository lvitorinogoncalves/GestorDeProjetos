package com.gestorDeProjetos.controller;

import com.gestorDeProjetos.dto.MembroDTO;
import com.gestorDeProjetos.exceptions.ErrorResponse;
import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.service.MembroService;
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
@RequestMapping("/v1/membros")
@Tag(name = "Membro", description = "Gerenciamento de membros do sistema")
public class MembroController {

    @Autowired
    private MembroService membroService;

    @Operation(summary = "Listar todos os membros", description = "Retorna uma lista de todos os membros cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de membros retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Membro>> getAllMembros() {
        List<Membro> membros = membroService.findAll();
        return ResponseEntity.ok(membros);
    }

    @Operation(summary = "Buscar membro por ID", description = "Retorna um membro com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membro encontrado"),
            @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Membro> getMembroById(@PathVariable Long id) {
        Membro membro = membroService.findById(id);
        return ResponseEntity.ok(membro);
    }

    @Operation(summary = "Criar um novo membro", description = "Adiciona um novo membro ao sistema")
    @ApiResponse(responseCode = "201", description = "Membro criado com sucesso")
    @PostMapping
    public ResponseEntity<Membro> createMembro(@RequestBody Membro membro) {
        Membro savedMembro = membroService.save(membro);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMembro);
    }

    @Operation(summary = "Adicionar membro com DTO", description = "Adiciona um novo membro usando DTO e retorna o membro criado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membro adicionado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao adicionar membro")
    })
    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarMembro(@RequestBody MembroDTO membroDTO) {
        try {
            MembroDTO novoMembro = membroService.adicionarMembro(membroDTO);
            return ResponseEntity.ok(novoMembro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro ao adicionar membro"));
        }
    }

    @Operation(summary = "Atualizar um membro", description = "Atualiza as informações de um membro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Membro> updateMembro(@PathVariable Long id, @RequestBody Membro membro) {
        membro.setId(id);
        Membro updatedMembro = membroService.save(membro);
        return ResponseEntity.ok(updatedMembro);
    }

    @Operation(summary = "Excluir membro por ID", description = "Exclui um membro com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Membro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembro(@PathVariable Long id) {
        membroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
