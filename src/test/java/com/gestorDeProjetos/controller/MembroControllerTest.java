package com.gestorDeProjetos.controller;

import com.gestorDeProjetos.dto.MembroDTO;
import com.gestorDeProjetos.exceptions.ErrorResponse;
import com.gestorDeProjetos.service.MembroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MembroControllerTest {

    @InjectMocks
    private MembroController membroController;

    @Mock
    private MembroService membroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarMembro_deveRetornarMembroDTO() {
        MembroDTO membroDTO = new MembroDTO();
        membroDTO.setNome("João");
        membroDTO.setAtribuicao("Desenvolvedor");

        when(membroService.adicionarMembro(any(MembroDTO.class))).thenReturn(membroDTO);

        ResponseEntity<?> result = membroController.adicionarMembro(membroDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof MembroDTO);
        MembroDTO responseMembro = (MembroDTO) result.getBody();
        assertEquals("João", responseMembro.getNome());
        assertEquals("Desenvolvedor", responseMembro.getAtribuicao());
        verify(membroService, times(1)).adicionarMembro(membroDTO);
    }

    @Test
    void adicionarMembro_deveRetornarErrorResponse_quandoErroAoAdicionar() {
        MembroDTO membroDTO = new MembroDTO();
        membroDTO.setNome("João");
        membroDTO.setAtribuicao("Desenvolvedor");

        when(membroService.adicionarMembro(any(MembroDTO.class)))
                .thenThrow(new RuntimeException("Erro ao adicionar membro"));

        ResponseEntity<?> result = membroController.adicionarMembro(membroDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Erro ao adicionar membro", errorResponse.getMessage());
    }
}
