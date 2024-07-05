package br.com.ienh.springacessobanco.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoacaoDTO(
        @NotNull
        @Size(max = 40, min = 5, message = "O doador deve ter entre 5 e 40 caracteres.")
        String doador,

        Integer livro_id,
        LivroDTO livro
) {
}
