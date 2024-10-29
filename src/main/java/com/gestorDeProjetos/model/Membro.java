package com.gestorDeProjetos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    @JoinColumn(name = "idpessoa", nullable = false)
    private Pessoa pessoa;

    @Column(length = 100)
    private String atribuicao;
}
