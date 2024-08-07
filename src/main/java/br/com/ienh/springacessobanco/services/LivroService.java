package br.com.ienh.springacessobanco.services;

import br.com.ienh.springacessobanco.dto.AutorDTO;
import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.dto.LivroDTO;
import br.com.ienh.springacessobanco.entities.Autor;
import br.com.ienh.springacessobanco.entities.Categoria;
import br.com.ienh.springacessobanco.entities.Livro;
import br.com.ienh.springacessobanco.repositories.AutorRepository;
import br.com.ienh.springacessobanco.repositories.CategoriaRepository;
import br.com.ienh.springacessobanco.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Iterable<Livro> obterTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> obterPorId(int id) {
        return livroRepository.findById(id);
    }

    public Livro salvar(LivroDTO livroDTO) {
        Livro novoLivro = new Livro();
        atualizarLivroComDTO(novoLivro, livroDTO);
        return livroRepository.save(novoLivro);
    }

    public Livro atualizar(int id, LivroDTO livroDTO) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de livro inválido: " + id));

        atualizarLivroComDTO(livroExistente, livroDTO);
        return livroRepository.save(livroExistente);
    }

    public void deletar(int id) {
        livroRepository.deleteById(id);
    }

    public List<CategoriaDTO> obterTodasCategorias() {
        List<CategoriaDTO> categoriaDTOs = new ArrayList<>();
        categoriaRepository.findAll().forEach(categoria ->
                categoriaDTOs.add(new CategoriaDTO(categoria.getId(), categoria.getNome())));
        return categoriaDTOs;
    }

    public List<AutorDTO> obterTodosAutores() {
        List<AutorDTO> autorDTOs = new ArrayList<>();
        autorRepository.findAll().forEach(autor ->
                autorDTOs.add(new AutorDTO(autor.getId(), autor.getNome())));
        return autorDTOs;
    }

    private void atualizarLivroComDTO(Livro livro, LivroDTO livroDTO) {
        livro.setTitulo(livroDTO.titulo());
        livro.setEditora(livroDTO.editora());

        Categoria categoria = categoriaRepository.findById(livroDTO.categoria_id())
                .orElseThrow(() -> new IllegalArgumentException("Categoria inválida: " + livroDTO.categoria_id()));
        livro.setCategoria(categoria);

        Autor autor = autorRepository.findById(livroDTO.autor_id())
                .orElseThrow(() -> new IllegalArgumentException("Autor inválido: " + livroDTO.autor_id()));
        livro.setAutor(autor);
    }
}
