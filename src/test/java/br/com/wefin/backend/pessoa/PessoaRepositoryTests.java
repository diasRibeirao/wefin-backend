package br.com.wefin.backend.pessoa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.wefin.backend.controller.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class PessoaRepositoryTests {

    private static final Long ID_TESTE = Long.valueOf(1);

    @Autowired
    PessoaRepository repository;

    @Test
    @Order(1)
    @DisplayName("Deve retornar registros ao buscar todas as pessoas")
    public void deveRetornarVazioBuscarTodasAsPessoas() {
        List<Pessoa> pessoas = repository.findAll();
        assertTrue(pessoas.isEmpty(), "A lista deve estar vazia");
    }

    @Test
    @Order(2)
    @DisplayName("Deve cadastrar uma pessoa")
    public void deveCadasrtarPessoa() {
        Pessoa pessoa = repository.save(new Pessoa("Fulado de Tal", "52511828057"));
        assertThat(pessoa.getId()).isEqualTo(ID_TESTE);
    }

    @Test
    @Order(3)
    @DisplayName("Buscar uma pessoa")
    public void deveBuscarUmaPessoa() {
        Pessoa pessoa = repository.findById(ID_TESTE).orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + ID_TESTE));
        assertThat(pessoa.getId()).isEqualTo(ID_TESTE);
    }

    @Test
    @Order(4)
    @DisplayName("Deve retornar registros ao buscar todas as pessoas")
    public void deveRetornarRegistrosBuscarTodasAsPessoas() {
        List<Pessoa> pessoas = repository.findAll();
        assertFalse(pessoas.isEmpty(), "A lista não deve estar vazia");
    }

    @Test
    @Order(5)
    @DisplayName("Deve atualizar uma pessoa")
    public void deveAtualizarPessos() {
        Pessoa pessoa = repository.findById(ID_TESTE).orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + ID_TESTE));
        pessoa.setNome("Pessoa Outro Nome");
        repository.save(pessoa);

        pessoa = repository.findById(ID_TESTE).orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + ID_TESTE));
        assertThat(pessoa.getNome()).isEqualTo("Pessoa Outro Nome");
    }

    @Test
    @Order(6)
    @DisplayName("Deve excluir uma pessoa")
    public void deveDeletar() {
        repository.deleteById(ID_TESTE);

        try {
            repository.findById(ID_TESTE).orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + ID_TESTE));
        } catch (ObjectNotFoundException e) {
            assertEquals("Pessoa não encontrada! Id: 1", e.getMessage());

        }
    }
}