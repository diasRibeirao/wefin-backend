package br.com.wefin.backend.pessoa.converter;

import br.com.wefin.backend.pessoa.Pessoa;
import br.com.wefin.backend.pessoa.PessoaAtualizarDTO;
import br.com.wefin.backend.pessoa.PessoaCadastrarDTO;
import br.com.wefin.backend.pessoa.PessoaDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaConverter {

    public PessoaDTO Parse(Pessoa origin) {
        if (origin == null)
            return null;

        return new PessoaDTO(origin.getId(), origin.getNome(), origin.getIdentificador(), origin.getTipoIdentificador());
    }

    public List<PessoaDTO> Parse(List<Pessoa> origin) {
        if (origin == null)
            return null;

        return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toList());
    }

    public Pessoa ParseCadastrarDTO(PessoaCadastrarDTO origin) {
        if (origin == null)
            return null;

        return new Pessoa(origin.nome(), origin.identificador());
    }


    public Pessoa ParseAtualizarDTO(PessoaAtualizarDTO origin) {
        if (origin == null)
            return null;

        return new Pessoa(origin.nome(), origin.identificador());
    }
}
