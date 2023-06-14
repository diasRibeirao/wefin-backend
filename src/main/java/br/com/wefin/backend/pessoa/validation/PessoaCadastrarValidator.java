package br.com.wefin.backend.pessoa.validation;

import br.com.wefin.backend.controller.exceptions.FieldMessage;
import br.com.wefin.backend.controller.utils.Utils;
import br.com.wefin.backend.pessoa.Pessoa;
import br.com.wefin.backend.pessoa.PessoaCadastrarDTO;
import br.com.wefin.backend.pessoa.PessoaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PessoaCadastrarValidator implements ConstraintValidator<PessoaCadastrar, PessoaCadastrarDTO> {

    @Autowired
    private PessoaRepository repository;

    @Override
    public void initialize(PessoaCadastrar annotation) {
    }

    @Override
    public boolean isValid(PessoaCadastrarDTO pessoaCadastrarDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (StringUtils.isBlank(pessoaCadastrarDTO.identificador())) {
            list.add(new FieldMessage("identificador", "O identificador é obrigatório"));
        } else {
            if (!Utils.isCpfValid(pessoaCadastrarDTO.identificador()) && !Utils.isCnpjValid(pessoaCadastrarDTO.identificador())) {
                list.add(new FieldMessage("identificador", "CPF ou CNPJ inválido"));
            } else {
                Pessoa aux = repository.findByIdentificador(pessoaCadastrarDTO.identificador());
                if (aux != null) {
                    list.add(new FieldMessage("identificador", "CPF ou CNPJ já cadastrado"));
                }
            }
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
