package com.gestorDeProjetos.controller;

import com.gestorDeProjetos.dto.MembroDTO;
import com.gestorDeProjetos.dto.ProjetoDTO;
import com.gestorDeProjetos.model.Membro;
import com.gestorDeProjetos.model.Pessoa;
import com.gestorDeProjetos.service.MembroProjetoService;
import com.gestorDeProjetos.service.MembroService;
import com.gestorDeProjetos.service.PessoaService;
import com.gestorDeProjetos.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Gestor de Projetos", description = "Controlador para gestão de projetos")
public class GestorDeProjetosController {

    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private MembroService membroService;
    @Autowired
    private MembroProjetoService membroProjetoService;

    @Operation(summary = "Listar Projetos", description = "Lista todos os projetos e exibe no template index.")
    @GetMapping("/")
    public String listarProjetosWeb(Model model) {
        List<ProjetoDTO> projetosFormatados = projetoService.listarProjetos().stream().map(projeto -> {
            ProjetoDTO projetoDTO = new ProjetoDTO();
            projetoDTO.setId(projeto.getId());
            projetoDTO.setNome(projeto.getNome());
            projetoDTO.setStatus(projeto.getStatus());
            projetoDTO.setDataInicioFormatada(projeto.getDataInicio() != null
                    ? projeto.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "Não informada");
            return projetoDTO;
        }).collect(Collectors.toList());

        model.addAttribute("projetos", projetosFormatados);
        return "index";
    }

    @Operation(summary = "Formulário de Cadastro de Projeto", description = "Exibe o formulário para cadastrar um novo projeto.")
    @GetMapping("/projeto/cadastrar")
    public String exibirFormularioCadastro(Model model) {
        List<Pessoa> gerentes = pessoaService.findAll();
        model.addAttribute("gerentes", gerentes);
        model.addAttribute("projeto", new ProjetoDTO());
        return "cadastro-projeto-form";
    }

    @Operation(summary = "Salvar Projeto", description = "Cria um novo projeto a partir do formulário.")
    @PostMapping("/projeto/salvar")
    public String criarProjeto(@ModelAttribute ProjetoDTO projetoDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cadastro-projeto-form"; // Voltar ao formulário em caso de erro
        }
        projetoService.criarProjeto(projetoDTO);
        redirectAttributes.addFlashAttribute("mensagem", "Projeto criado com sucesso.");
        return "redirect:/";
    }

    @Operation(summary = "Editar Projeto", description = "Exibe o formulário para editar um projeto existente.")
    @GetMapping("/projeto/editar/{id}")
    public String editarProjeto(@PathVariable Long id, Model model) {
        ProjetoDTO projetoDTO = projetoService.findById(id);
        List<Pessoa> gerentes = pessoaService.findAll();
        model.addAttribute("gerentes", gerentes);
        model.addAttribute("projeto", projetoDTO);
        return "cadastro-projeto-form";
    }

    @Operation(summary = "Atualizar Projeto", description = "Atualiza os dados de um projeto existente.")
    @PostMapping("/projeto/atualizar")
    public String atualizarProjeto(@ModelAttribute("projeto") ProjetoDTO projetoDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cadastro-projeto-form"; // Voltar ao formulário em caso de erro
        }
        projetoService.atualizarProjeto(projetoDTO.getId(), projetoDTO);
        redirectAttributes.addFlashAttribute("mensagem", "Projeto atualizado com sucesso.");
        return "redirect:/";
    }

    @Operation(summary = "Excluir Projeto", description = "Exclui um projeto pelo seu ID.")
    @PostMapping("/projeto/excluir/{id}")
    public String excluirProjetoPorId(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean excluido = projetoService.excluirProjeto(id);
        if (!excluido) {
            redirectAttributes.addFlashAttribute("mensagem", "Não é possível excluir um projeto com status iniciado, em andamento ou encerrado.");
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Projeto excluído com sucesso.");
        }
        return "redirect:/";
    }

    @Operation(summary = "Associar Membros a Projeto", description = "Exibe a tela para associar membros a um projeto.")
    @GetMapping("/projetos/{projetoId}/associarMembros")
    public String mostrarAssociarMembros(@PathVariable Long projetoId, Model model) {
        List<Membro> membrosDisponiveis = membroProjetoService.getMembrosNaoAssociadosAoProjeto(projetoId);
        List<Membro> membrosAssociados = membroProjetoService.getMembrosAssociadosAoProjeto(projetoId);
        model.addAttribute("projetoId", projetoId);
        model.addAttribute("membrosDisponiveis", membrosDisponiveis);
        model.addAttribute("membrosAssociados", membrosAssociados);
        return "associar-membros";
    }
}
