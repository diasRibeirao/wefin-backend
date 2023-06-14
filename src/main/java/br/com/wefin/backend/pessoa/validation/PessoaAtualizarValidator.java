package br.com.wefin.backend.pessoa.validation;

import br.com.wefin.backend.controller.exceptions.FieldMessage;
import br.com.wefin.backend.controller.utils.Utils;
import br.com.wefin.backend.pessoa.Pessoa;
import br.com.wefin.backend.pessoa.PessoaAtualizarDTO;
import br.com.wefin.backend.pessoa.PessoaCadastrarDTO;
import br.com.wefin.backend.pessoa.PessoaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PessoaAtualizarValidator implements ConstraintValidator<PessoaAtualizar, PessoaAtualizarDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PessoaRepository repository;

    public void initialize(PessoaCadastrar annotation) {
    }

    @Override
    public boolean isValid(PessoaAtualizarDTO pessoaAtualizarDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (StringUtils.isBlank(pessoaAtualizarDTO.identificador())) {
            list.add(new FieldMessage("identificador", "O identificador é obrigatório"));
        } else {
            if (!Utils.isCpfValid(pessoaAtualizarDTO.identificador()) && !Utils.isCnpjValid(pessoaAtualizarDTO.identificador())) {
                list.add(new FieldMessage("identificador", "CPF ou CNPJ inválido"));
            } else {
                Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                Long uriId = Long.parseLong(map.get("id"));

                Pessoa aux = repository.findByIdentificador(pessoaAtualizarDTO.identificador());
                if (aux != null && !aux.getId().equals(uriId)) {
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
