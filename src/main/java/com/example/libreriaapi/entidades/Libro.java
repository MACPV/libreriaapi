package com.example.libreriaapi.entidades;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Libro {

    @Id
    private Integer idLibro;

    @Column
    private Integer ejemplares;

    @Column
    private boolean libroActivo;

    @Column
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "autor_id_autor", nullable = false)
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "editorial_id_editorial", nullable = false)
    private Editorial editorial;
}
