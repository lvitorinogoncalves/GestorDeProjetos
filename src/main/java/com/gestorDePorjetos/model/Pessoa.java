package com.gestorDePorjetos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    private LocalDate dataNascimento;

    @Column(length = 14)
    private String cpf;

    private boolean funcionario;
    private boolean gerente;
}
