package br.com.ienh.springacessobanco.services;

import br.com.ienh.springacessobanco.dto.AutorDTO;
import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.dto.DoacaoDTO;
import br.com.ienh.springacessobanco.dto.LivroDTO;
import br.com.ienh.springacessobanco.entities.Autor;
import br.com.ienh.springacessobanco.entities.Categoria;
import br.com.ienh.springacessobanco.entities.Doacao;
import br.com.ienh.springacessobanco.entities.Livro;
import br.com.ienh.springacessobanco.repositories.DoacaoRepository;
import br.com.ienh.springacessobanco.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoacaoService {
    @Autowired
    DoacaoRepository doacaoRepository;
    @Autowired
    LivroRepository livroRepository;
    public Iterable<Doacao> obterTodos() {
        return doacaoRepository.findAll();
    }
    public Optional<Doacao> obterPorId(int id) {
        return doacaoRepository.findById(id);
    }
    public Doacao salvar(DoacaoDTO doacaoDTO) {
        Doacao novaDoacao = new Doacao();
        atualizarDoacaoComDTO(novaDoacao, doacaoDTO);
        return doacaoRepository.save(novaDoacao);
    }

    public void deletar(int id) {
        doacaoRepository.deleteById(id);
    }

    public List<LivroDTO> obterTodosLivros() {
        List<LivroDTO> livros = new ArrayList<LivroDTO>();
        livroRepository.findAll().forEach(livro -> livros.add(new LivroDTO(livro.getId(), livro.getTitulo(), livro.getEditora(), livro.getCategoria().getId(), livro.getAutor().getId(), new CategoriaDTO(livro.getCategoria().getId(), livro.getCategoria().getNome()), new AutorDTO(livro.getAutor().getId(), livro.getAutor().getNome()))));
        return livros;
    }

    private void atualizarDoacaoComDTO(Doacao doacao, DoacaoDTO doacaoDTO) {
        doacao.setDoador(doacaoDTO.doador());

        Livro livro = livroRepository.findById(doacaoDTO.livro_id())
                .orElseThrow(() -> new IllegalArgumentException("Livro inválido: " + doacaoDTO.livro_id()));
        doacao.setLivro(livro);
    }

    public Doacao atualizar(int id, DoacaoDTO doacaoDTO) {
        Doacao doacaoExistente = doacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de livro inválido: " + id));

        atualizarDoacaoComDTO(doacaoExistente, doacaoDTO);
        return doacaoRepository.save(doacaoExistente);
    }
}
