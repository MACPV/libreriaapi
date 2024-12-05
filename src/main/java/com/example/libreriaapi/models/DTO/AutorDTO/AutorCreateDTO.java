package com.example.libreriaapi.models.DTO.AutorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AutorCreateDTO {

    private String nombreAutor;
    private boolean autorActivo;

}
