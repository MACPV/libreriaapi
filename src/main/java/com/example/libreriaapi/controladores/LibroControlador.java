package com.example.libreriaapi.controladores;

import com.example.libreriaapi.models.DTO.LibroDTO.LibroCreateDTO;
import com.example.libreriaapi.models.DTO.LibroDTO.LibroListarActivosDTO;
import com.example.libreriaapi.models.DTO.LibroDTO.LibroModificarDTO;
import com.example.libreriaapi.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @PostMapping("/crear")
    public ResponseEntity<Object> crearLibro(
            @RequestBody LibroCreateDTO libroCreateDTO
    ) {
        try {
            libroServicio.crearLibro(libroCreateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Algun dato no es correcto o es nulo, revisar.\"}");
        }
    }

    @GetMapping("/listarActivos")
    public ResponseEntity<Object> listarLibro() {
        try {
            List<LibroListarActivosDTO> libroListarActivosDTOS = libroServicio.librosActivos();
            return ResponseEntity.ok(libroListarActivosDTOS);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/modificar")
    public ResponseEntity<Void> modificarLibro(
            @RequestBody LibroModificarDTO libroModificarDTO
    ) {
        try {
            libroServicio.modificarLibro(libroModificarDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/baja")
    public ResponseEntity<Void> bajaLibro(
            @RequestParam Integer idLibro
    ) {
        try {
            libroServicio.darBaja(idLibro);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
