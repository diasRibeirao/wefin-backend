package br.com.wefin.backend.controller;

import br.com.wefin.backend.controller.exceptions.ObjectNotFoundException;
import br.com.wefin.backend.pessoa.*;
import br.com.wefin.backend.pessoa.converter.PessoaConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private PessoaConverter converter;

    @GetMapping
    @Operation(summary = "Listar todas as pessoas")
    public ResponseEntity<List<PessoaDTO>> findAll(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        List<PessoaDTO> list = converter.Parse(repository.findAll());
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Buscar uma pessoa pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> find(@Valid @PathVariable Long id) {
        PessoaDTO obj = converter.Parse(repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + id)));
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Cadastrar uma pessoa")
    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrar(@Valid  @RequestBody PessoaCadastrarDTO dados) {
        Pessoa pessoa = converter.ParseCadastrarDTO(dados);
        pessoa = repository.save(pessoa);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pessoa.getId())
                    .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualizar uma pessoa")
    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Void> update(@Valid @RequestBody PessoaAtualizarDTO pessoaAtualizarDTO,
                                       @Valid @PathVariable Long id) {
        Pessoa pessoaSalva = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + id));

        Pessoa pessoa = converter.ParseAtualizarDTO(pessoaAtualizarDTO);
        pessoa.setId(pessoaSalva.getId());
        pessoa = repository.save(pessoa);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar uma pessoa")
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + id));

        repository.delete(pessoa);
        return ResponseEntity.noContent().build();
    }
}
