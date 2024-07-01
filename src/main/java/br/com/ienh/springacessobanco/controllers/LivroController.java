package br.com.ienh.springacessobanco.controllers;

import br.com.ienh.springacessobanco.dto.*;
import br.com.ienh.springacessobanco.entities.*;
import br.com.ienh.springacessobanco.repositories.AutorRepository;
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
    @Autowired
    AutorRepository autorRepository;

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
        Iterable<Autor> autores = autorRepository.findAll();
        List<AutorDTO> autorDTO = new ArrayList<>();
        autores.forEach(autor -> autorDTO.add(
                new AutorDTO(autor.getId(), autor.getNome())));
        model.addAttribute("autores", autorDTO);
        return "/livro/novoForm";
    }

    @PostMapping("/novo")
    public String novoSalvar(@Valid @ModelAttribute("livro") LivroDTO livroDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) {
            Iterable<Categoria> categorias = categoriaRepository.findAll();
            List<CategoriaDTO> categoriaDTO = new ArrayList<>();
            categorias.forEach(categoria -> categoriaDTO.add(
                    new CategoriaDTO(categoria.getId(), categoria.getNome())));
            model.addAttribute("categorias", categoriaDTO);

            Iterable<Autor> autores = autorRepository.findAll();
            List<AutorDTO> autorDTO = new ArrayList<>();
            autores.forEach(autor -> autorDTO.add(
                    new AutorDTO(autor.getId(), autor.getNome())));
            model.addAttribute("autores", autorDTO);

            return "/livro/novoForm";
        }

        Livro novoLivro = new Livro();
        novoLivro.setTitulo(livroDTO.titulo());
        novoLivro.setEditora(livroDTO.editora());

        Categoria categoria = categoriaRepository.findById(livroDTO.categoria_id())
                .orElseThrow(() -> new IllegalArgumentException("Categoria inválida: " + livroDTO.categoria_id()));
        novoLivro.setCategoria(categoria);

        Autor autor = autorRepository.findById(livroDTO.autor_id())
                .orElseThrow(() -> new IllegalArgumentException("Autor inválido: " + livroDTO.autor_id()));
        novoLivro.setAutor(autor);

        livroService.salvar(novoLivro);

        return "redirect:/livro/listar";
    }
}
