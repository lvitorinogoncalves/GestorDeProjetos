package com.gestorDePorjetos.controller;

import com.gestorDePorjetos.model.Projeto;
import com.gestorDePorjetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public List<Projeto> listarProjetos() {
        return projetoService.listarProjetos();
    }

    @PostMapping
    public Projeto criarProjeto(@RequestBody Projeto projeto) {
        return projetoService.criarProjeto(projeto);
    }

    @PutMapping("/{id}")
    public Projeto atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        return projetoService.atualizarProjeto(id, projeto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirProjeto(@PathVariable Long id) {
        boolean deletado = projetoService.excluirProjeto(id);
        if (deletado) {
            return ResponseEntity.ok("Projeto excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não é possível excluir este projeto.");
    }
}
