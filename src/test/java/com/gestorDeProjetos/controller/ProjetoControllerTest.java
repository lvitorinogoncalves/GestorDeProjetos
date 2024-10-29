package com.gestorDeProjetos.controller;

import com.gestorDeProjetos.dto.ProjetoDTO;
import com.gestorDeProjetos.exceptions.ErrorResponse;
import com.gestorDeProjetos.exceptions.ProjetoNotFoundException;
import com.gestorDeProjetos.model.Pessoa;
import com.gestorDeProjetos.service.PessoaService;
import com.gestorDeProjetos.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProjetoControllerTest {

    @InjectMocks
    private ProjetoController projetoController;

    @Mock
    private ProjetoService projetoService;

    @Mock
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarProjetos_deveRetornarListaDeProjetos() {
        ProjetoDTO projeto1 = new ProjetoDTO();
        projeto1.setId(1L);
        projeto1.setNome("Projeto 1");

        ProjetoDTO projeto2 = new ProjetoDTO();
        projeto2.setId(2L);
        projeto2.setNome("Projeto 2");

        List<ProjetoDTO> projetos = List.of(projeto1, projeto2);
        when(projetoService.listarProjetos()).thenReturn(projetos);

        ResponseEntity<List<ProjetoDTO>> response = projetoController.listarProjetos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(projetoService, times(1)).listarProjetos();
    }

    @Test
    void criarProjeto_deveRetornarProjetoCriado() {
        Long gerenteId = 1L;
        Pessoa gerente = new Pessoa();
        gerente.setId(gerenteId);
        gerente.setNome("Gerente Teste");

        when(pessoaService.findById(gerenteId)).thenReturn(gerente);

        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setNome("Novo Projeto");
        projetoDTO.setIdGerente(gerenteId);

        ProjetoDTO projetoCriado = new ProjetoDTO();
        projetoCriado.setId(1L);
        projetoCriado.setNome("Novo Projeto");

        when(projetoService.criarProjeto(any(ProjetoDTO.class))).thenReturn(projetoCriado);

        ResponseEntity<ProjetoDTO> response = projetoController.criarProjeto(projetoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Novo Projeto", response.getBody().getNome());
        verify(projetoService, times(1)).criarProjeto(projetoDTO);
    }

    @Test
    void atualizarProjeto_deveRetornarProjetoAtualizado() {
        Long projetoId = 1L;
        ProjetoDTO projetoAtualizadoDTO = new ProjetoDTO();
        projetoAtualizadoDTO.setNome("Projeto Atualizado");

        ProjetoDTO projetoAtualizado = new ProjetoDTO();
        projetoAtualizado.setId(projetoId);
        projetoAtualizado.setNome("Projeto Atualizado");

        when(projetoService.atualizarProjeto(eq(projetoId), any(ProjetoDTO.class)))
                .thenReturn(projetoAtualizado);

        ResponseEntity<?> response = projetoController.atualizarProjeto(projetoId, projetoAtualizadoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Projeto Atualizado", ((ProjetoDTO) response.getBody()).getNome());
        verify(projetoService, times(1)).atualizarProjeto(projetoId, projetoAtualizadoDTO);
    }

    @Test
    void atualizarProjeto_deveRetornarNotFound_quandoProjetoNaoExistir() {
        Long projetoId = 1L;
        ProjetoDTO projetoAtualizadoDTO = new ProjetoDTO();

        when(projetoService.atualizarProjeto(eq(projetoId), any(ProjetoDTO.class)))
                .thenThrow(new ProjetoNotFoundException("Projeto não encontrado"));

        ResponseEntity<?> response = projetoController.atualizarProjeto(projetoId, projetoAtualizadoDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(ErrorResponse.class, response.getBody());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Projeto não encontrado", errorResponse.getMessage());
    }

    @Test
    void excluirProjeto_deveRetornarOk_quandoProjetoExistir() {
        Long projetoId = 1L;

        when(projetoService.excluirProjeto(projetoId)).thenReturn(true);

        ResponseEntity<?> response = projetoController.excluirProjeto(projetoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Projeto excluído com sucesso.", response.getBody());
        verify(projetoService, times(1)).excluirProjeto(projetoId);
    }


    @Test
    void excluirProjeto_deveRetornarNotFound_quandoProjetoNaoExistir() {
        Long projetoId = 1L;

        doThrow(new ProjetoNotFoundException("Projeto não encontrado")).when(projetoService).excluirProjeto(projetoId);

        ResponseEntity<?> response = projetoController.excluirProjeto(projetoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        assertEquals("Projeto não encontrado", ((ErrorResponse) response.getBody()).getMessage());
    }
}
