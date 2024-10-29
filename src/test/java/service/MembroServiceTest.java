package service;

import com.gestorDeProjetos.dto.MembroDTO;
import com.gestorDeProjetos.mapper.MembroMapper;
import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.model.Pessoa;
import com.gestorDeProjetos.repository.MembroRepository;
import com.gestorDeProjetos.repository.PessoaRepository;
import com.gestorDeProjetos.service.MembroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembroServiceTest {

    @Mock
    private MembroRepository membroRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private MembroMapper membroMapper;

    @InjectMocks
    private MembroServiceImpl membroServiceImpl;

    private Membro membro;
    private MembroDTO membroDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        membro = new Membro();
        membroDTO = new MembroDTO();
        membroDTO.setAtribuicao("Desenvolvedor");
        membro.setPessoa(new Pessoa());
        membro.getPessoa().setNome("João");
    }

    @Test
    void adicionarMembro_deveRetornarMembroDTO() {
        membroDTO.setNome("João");
        membroDTO.setAtribuicao("Desenvolvedor");

        when(membroMapper.toEntity(membroDTO)).thenReturn(membro);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(membro.getPessoa()); // Mock para pessoaRepository
        when(membroRepository.save(membro)).thenReturn(membro);
        when(membroMapper.toDTO(membro)).thenReturn(membroDTO);

        MembroDTO result = membroServiceImpl.adicionarMembro(membroDTO);

        assertNotNull(result);
        assertEquals("João", result.getNome());
        verify(membroRepository, times(1)).save(membro);
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void adicionarMembro_deveLancarExcecao_quandoNomeEhNulo() {
        membroDTO.setNome(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            membroServiceImpl.adicionarMembro(membroDTO);
        });

        assertEquals("O nome do membro não pode ser vazio.", exception.getMessage());
        verify(membroRepository, never()).save(any(Membro.class));
    }

    @Test
    void findAll_deveRetornarListaDeMembros() {
        when(membroRepository.findAll()).thenReturn(List.of(membro));

        List<Membro> result = membroServiceImpl.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(membroRepository, times(1)).findAll();
    }

    @Test
    void findById_deveRetornarMembro_quandoMembroExistir() {
        Long id = 1L;
        when(membroRepository.findById(id)).thenReturn(Optional.of(membro));

        Membro result = membroServiceImpl.findById(id);

        assertNotNull(result);
        assertEquals(membro, result);
        verify(membroRepository, times(1)).findById(id);
    }

    @Test
    void findById_deveRetornarNull_quandoMembroNaoExistir() {
        Long id = 1L;
        when(membroRepository.findById(id)).thenReturn(Optional.empty());

        Membro result = membroServiceImpl.findById(id);

        assertNull(result);
        verify(membroRepository, times(1)).findById(id);
    }

    @Test
    void delete_deveRemoverMembro_quandoMembroExistir() {
        Long id = 1L;
        doNothing().when(membroRepository).deleteById(id);

        membroServiceImpl.delete(id);

        verify(membroRepository, times(1)).deleteById(id);
    }
}