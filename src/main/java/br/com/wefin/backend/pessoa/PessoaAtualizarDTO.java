package br.com.wefin.backend.pessoa;

import br.com.wefin.backend.pessoa.validation.PessoaAtualizar;
import br.com.wefin.backend.pessoa.validation.PessoaCadastrar;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@PessoaAtualizar
public record PessoaAtualizarDTO(
        @NotEmpty(message = "O nome é obrigatório")
        @Size(min = 5, max = 100, message = "O nome deve possuir entre {min} e {max} caracteres")
        String nome,

        String identificador
) {
}
