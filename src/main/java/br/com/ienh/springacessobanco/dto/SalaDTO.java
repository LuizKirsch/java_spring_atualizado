package br.com.ienh.springacessobanco.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record SalaDTO (
        Integer id,

        @Min(value = 1, message = "A sala deve ser maior ou igual a 1.")
        Integer sala,

        @Size(max = 3, min = 3, message = "Selecione 'sim' ou 'não'.")
        String disponivel
){
}
