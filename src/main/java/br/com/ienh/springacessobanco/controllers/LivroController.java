package br.com.ienh.springacessobanco.controllers;

import br.com.ienh.springacessobanco.dto.*;
import br.com.ienh.springacessobanco.entities.*;
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
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    LivroService livroService;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("livros", livroService.obterTodos());
        return "/livro/listar";
    }

    @GetMapping("/novo")
    public String novoForm(@ModelAttribute("livro") LivroDTO livro, Model model){
        carregarCategoriasEAutores(model);
        return "/livro/novoForm";
    }

    @PostMapping("/novo")
    public String novoSalvar(@Valid @ModelAttribute("livro") LivroDTO livroDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            carregarCategoriasEAutores(model);
            return "/livro/novoForm";
        }

        livroService.salvar(livroDTO);
        return "redirect:/livro/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") int id, Model model) {
        Livro livro = livroService.obterPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de livro inválido: " + id));

        LivroDTO livroDTO = new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getEditora(),
                livro.getCategoria().getId(),
                livro.getAutor().getId(),
                new CategoriaDTO(livro.getCategoria().getId(), livro.getCategoria().getNome()),
                new AutorDTO(livro.getAutor().getId(), livro.getAutor().getNome())
        );

        model.addAttribute("livro", livroDTO);
        carregarCategoriasEAutores(model);

        return "/livro/editarForm";
    }

    @PostMapping("/editar/{id}")
    public String editarSalvar(@PathVariable("id") int id, @Valid @ModelAttribute("livro") LivroDTO livroDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            carregarCategoriasEAutores(model);
            return "/livro/editarForm";
        }

        livroService.atualizar(id, livroDTO);
        return "redirect:/livro/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") int id) {
        livroService.deletar(id);
        return "redirect:/livro/listar";
    }

    private void carregarCategoriasEAutores(Model model) {
        Iterable<Categoria> categorias = livroService.obterTodasCategorias();
        List<CategoriaDTO> categoriaDTO = new ArrayList<>();
        categorias.forEach(categoria -> categoriaDTO.add(
                new CategoriaDTO(categoria.getId(), categoria.getNome())));
        model.addAttribute("categorias", categoriaDTO);

        Iterable<Autor> autores = livroService.obterTodosAutores();
        List<AutorDTO> autorDTO = new ArrayList<>();
        autores.forEach(autor -> autorDTO.add(
                new AutorDTO(autor.getId(), autor.getNome())));
        model.addAttribute("autores", autorDTO);
    }
}
