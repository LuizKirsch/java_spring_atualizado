package br.com.ienh.springacessobanco.repositories;

import br.com.ienh.springacessobanco.entities.Doacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoRepository extends CrudRepository<Doacao, Integer> {
}
