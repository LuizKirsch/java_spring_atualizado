package br.com.ienh.springacessobanco.services;

import br.com.ienh.springacessobanco.dto.AutorDTO;
import br.com.ienh.springacessobanco.dto.CategoriaDTO;
import br.com.ienh.springacessobanco.dto.SalaDTO;
import br.com.ienh.springacessobanco.entities.Autor;
import br.com.ienh.springacessobanco.entities.Categoria;
import br.com.ienh.springacessobanco.entities.Sala;
import br.com.ienh.springacessobanco.repositories.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaService {
    @Autowired
    SalaRepository salaRepository;

    public List<SalaDTO> obterTodos() {
        List<SalaDTO> salas = new ArrayList<>();
        salaRepository.findAll().forEach(sala -> {
            SalaDTO salaDTO = new SalaDTO(sala.getId(), sala.getSala(), sala.getDisponivel());
            salas.add(salaDTO);
        });
        return salas;
    }

    public void excluirSala(int id) {
        salaRepository.deleteById(id);
    }

    public void salvarSala(SalaDTO sala) {
        Sala novaSala = new Sala();
        novaSala.setSala(sala.sala());
        novaSala.setDisponivel(sala.disponivel());
        salaRepository.save(novaSala);
    }

    public SalaDTO obterSalaPorID(int id){
        SalaDTO salaDTO = null;
        Sala sala = salaRepository.findById(id).get();
        salaDTO = new SalaDTO(sala.getId(), sala.getSala(), sala.getDisponivel());
        return salaDTO;
    }

    public void atualizarSala(SalaDTO sala){
        Sala atualizarSala = new Sala();
        atualizarSala.setDisponivel(sala.disponivel());
        atualizarSala.setSala(sala.sala());
        atualizarSala.setId(sala.id());
        salaRepository.save(atualizarSala);
    }


}
