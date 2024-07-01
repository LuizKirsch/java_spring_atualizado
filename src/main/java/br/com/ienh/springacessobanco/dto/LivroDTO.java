package br.com.ienh.springacessobanco.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LivroDTO (
        Integer id,

        @NotNull
        @Size(max = 100, min = 5, message = "O titulo deve ter entre 5 e 100 caracteres.")
        String titulo,

        @NotNull
        @Size(max = 40, min = 5, message = "O editora deve ter entre 5 e 40 caracteres.")
        String editora,

        Integer categoria_id,

        Integer autor_id,

        CategoriaDTO categoria,

        AutorDTO autor
        ) {

}
