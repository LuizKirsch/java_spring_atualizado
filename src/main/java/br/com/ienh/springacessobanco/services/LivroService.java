package br.com.ienh.springacessobanco.services;

import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.dto.LivroDTO;
import br.com.ienh.springacessobanco.entities.Categoria;
import br.com.ienh.springacessobanco.entities.Livro;
import br.com.ienh.springacessobanco.repositories.AutorRepository;
import br.com.ienh.springacessobanco.repositories.CategoriaRepository;
import br.com.ienh.springacessobanco.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    AutorService autorService;
    @Autowired
    CategoriaService categoriaService;
    public List<LivroDTO> obterTodos(){
        List<LivroDTO> livros = new ArrayList<>();
        livroRepository.findAll().forEach(livro -> {
            LivroDTO livroDTO = new LivroDTO(
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getEditora(),
                    livro.getAutor().getId(),
                    livro.getCategoria().getId(),
                    categoriaService.obterCategoriaPorID(livro.getCategoria().getId()),
                    autorService.obterAutorPorID(livro.getAutor().getId())
            );
            livros.add(livroDTO);
        });
        return livros;
    }

    public void salvarLivro(LivroDTO livro){
        Livro novoLivro = new Livro();
        novoLivro.setTitulo(livro.titulo());
        novoLivro.setEditora(livro.editora());
        categoriaRepository.findById(livro.categoria_id()).ifPresent(novoLivro::setCategoria);
        autorRepository.findById(livro.autor_id()).ifPresent(novoLivro::setAutor);
    }

}
