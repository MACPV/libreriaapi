package com.example.libreriaapi.models.DTO.LibroDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroModificarDTO {

    private Integer idLibro;
    private String titulo;
    private Integer ejemplares;
    private String idAutor;
    private String idEditorial;


}
