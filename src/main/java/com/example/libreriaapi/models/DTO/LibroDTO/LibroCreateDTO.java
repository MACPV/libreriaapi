package com.example.libreriaapi.models.DTO.LibroDTO;

import lombok.Data;

@Data
public class LibroCreateDTO {

    private Integer isbn;
    private String titulo;
    private Integer ejemplares;
    private String idAutor;
    private String idEditorial;
    private boolean libroActivo;
}
