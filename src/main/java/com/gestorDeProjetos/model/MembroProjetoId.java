package com.gestorDeProjetos.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MembroProjetoId implements Serializable {

    private Long idMembro;
    private Long idProjeto;
}
