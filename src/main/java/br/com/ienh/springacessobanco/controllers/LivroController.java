package br.com.ienh.springacessobanco.controllers;

import br.com.ienh.springacessobanco.dto.AlunoDTO;
import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.dto.LivroDTO;
import br.com.ienh.springacessobanco.entities.Aluno;
import br.com.ienh.springacessobanco.entities.Categoria;
import br.com.ienh.springacessobanco.repositories.CategoriaRepository;
import br.com.ienh.springacessobanco.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    LivroService livroService;
    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("livros", livroService.obterTodos());
        return "/livro/listar";
    }

    @GetMapping("/novo")
    public String novoForm(@ModelAttribute("livro") LivroDTO livro, Model model){
        Iterable<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> categoriaDTO = new ArrayList<>();
        categorias.forEach(categoria -> categoriaDTO.add(
                new CategoriaDTO(categoria.getId(), categoria.getNome())));
        model.addAttribute("categorias", categoriaDTO);

        return "/livro/novoForm";
    }

    @PostMapping("/novo")
    public String novoSalvar(@Valid @ModelAttribute("livro") LivroDTO livro, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "/categoria/novoForm";
        livroService.salvarLivro(livro);
        return "redirect:/livro/listar";
    }
}
