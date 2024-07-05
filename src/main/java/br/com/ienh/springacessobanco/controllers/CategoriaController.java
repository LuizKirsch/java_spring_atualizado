package br.com.ienh.springacessobanco.controllers;

import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("categorias", categoriaService.obterTodos());
        return "/categoria/listar";
    }

    @GetMapping("/novo")
    public String novoForm(@ModelAttribute("categoria") CategoriaDTO categoria){
        return "/categoria/novoForm";
    }

    @PostMapping("/novo")
    public String novoSalvar(@Valid @ModelAttribute("categoria") CategoriaDTO categoria, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "/categoria/novoForm";
        categoriaService.salvarCategoria(categoria);
        return "redirect:/categoria/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable int id){
        categoriaService.excluixCategoria(id);
        return "redirect:/categoria/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable int id, Model model){
        model.addAttribute("categoria", categoriaService.obterCategoriaPorID(id));
        return "/categoria/editarForm";
    }

    @PostMapping("/editar")
    public String editarSalvar(@Valid @ModelAttribute("categoria") CategoriaDTO categoria, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "/categoria/editarForm";
        categoriaService.atualizarCategoria(categoria);
        return "redirect:/categoria/listar";
    }
}
