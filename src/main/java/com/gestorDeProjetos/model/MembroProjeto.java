package com.gestorDeProjetos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membro_projeto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembroProjeto {

    @EmbeddedId
    private MembroProjetoId id;

    @ManyToOne
    @MapsId("idMembro")
    @JoinColumn(name = "id_membro")
    private Membro membro;

    @ManyToOne
    @MapsId("idProjeto")
    @JoinColumn(name = "id_projeto")
    private Projeto projeto;
}
