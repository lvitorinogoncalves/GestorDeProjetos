package com.gestorDeProjetos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_nascimento")
    private java.time.LocalDate dataNascimento;

    @Column(length = 14)
    private String cpf;

    @Column(nullable = false)
    private Boolean funcionario;

    @Column(nullable = false)
    private Boolean gerente;

    public Pessoa(Long id) {
        this.id = id;
    }
}
