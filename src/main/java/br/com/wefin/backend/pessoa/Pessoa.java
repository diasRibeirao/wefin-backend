package br.com.wefin.backend.pessoa;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "pessoas")
@Entity(name = "Pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String identificador;
    @Enumerated(EnumType.STRING)
    private TipoIdentificador tipoIdentificador;

    public Pessoa(String nome, String identificador) {
        this.nome = nome;
        this.identificador = identificador;
        this.tipoIdentificador = identificador.length() == 11 ? TipoIdentificador.CPF : TipoIdentificador.CNPJ;
    }
}
