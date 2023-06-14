package br.com.wefin.backend.pessoa;

import lombok.AllArgsConstructor;


public record PessoaDTO(
        Long id,
        String nome,
        String identificador,
        TipoIdentificador tipoIdentificador) {
}
