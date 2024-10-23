package com.gestorDePorjetos.controller;

import com.gestorDePorjetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorDeProjetosController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/index")
    public String listarProjetosWeb(Model model) {
        model.addAttribute("projetos", projetoService.listarProjetos());
        return "index";
    }
}
