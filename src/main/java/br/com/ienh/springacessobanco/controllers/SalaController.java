package br.com.ienh.springacessobanco.controllers;

import br.com.ienh.springacessobanco.dto.AutorDTO;
import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.dto.SalaDTO;
import br.com.ienh.springacessobanco.services.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sala")
public class SalaController {
    @Autowired
    SalaService salaService;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("salas", salaService.obterTodos());
        return "/sala/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable int id){
        salaService.excluirSala(id);
        return "redirect:/sala/listar";
    }

    @GetMapping("/novo")
    public String novoForm(@ModelAttribute("sala") SalaDTO sala){
        return "/sala/novoForm";
    }

    @PostMapping("/novo")
    public String novoSalvar(@Valid @ModelAttribute("sala") SalaDTO sala, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "/sala/novoForm";
        salaService.salvarSala(sala);
        return "redirect:/sala/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable int id, Model model){
        model.addAttribute("sala", salaService.obterSalaPorID(id));
        return "/sala/editarForm";
    }

    @PostMapping("/editar")
    public String editarSalvar(@Valid @ModelAttribute("sala") SalaDTO sala, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "/sala/editarForm";
        salaService.atualizarSala(sala);
        return "redirect:/sala/listar";
    }

}
