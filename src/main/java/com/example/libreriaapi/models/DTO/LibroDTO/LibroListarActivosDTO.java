package com.example.libreriaapi.models.DTO.LibroDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroListarActivosDTO {

    private Integer idLibro;
    private String titulo;
    private Integer ejemplares;

}
