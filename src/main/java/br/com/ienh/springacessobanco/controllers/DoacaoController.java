package br.com.ienh.springacessobanco.controllers;

import br.com.ienh.springacessobanco.dto.AutorDTO;
import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.dto.DoacaoDTO;
import br.com.ienh.springacessobanco.dto.LivroDTO;
import br.com.ienh.springacessobanco.entities.Autor;
import br.com.ienh.springacessobanco.entities.Categoria;
import br.com.ienh.springacessobanco.entities.Livro;
import br.com.ienh.springacessobanco.services.DoacaoService;
import br.com.ienh.springacessobanco.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doacao")
public class DoacaoController {
    @Autowired
    DoacaoService doacaoService;
    @Autowired
    LivroService livroService;
    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("doacoes", doacaoService.obterTodos());
        return "/doacao/listar";
    }

    @GetMapping("/novo")
    public String novoForm(@ModelAttribute("doacao") DoacaoDTO doacao, Model model){
        carregarLivros(model);
        return "/doacao/novoForm";
    }

    @PostMapping("/novo")
    public String novoSalvar(@Valid @ModelAttribute("doacao") DoacaoDTO doacao, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            carregarLivros(model);
            return "/doacao/novoForm";
        }

        doacaoService.salvar(doacao);
        return "redirect:/doacao/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") int id) {
        doacaoService.deletar(id);
        return "redirect:/doacao/listar";
    }

    private void carregarLivros(Model model) {
        List<LivroDTO> livros = doacaoService.obterTodosLivros();
        model.addAttribute("livros", livros);
    }
}
