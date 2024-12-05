package com.example.libreriaapi.controladores;

import com.example.libreriaapi.entidades.Autor;
import com.example.libreriaapi.entidades.Libro;
import com.example.libreriaapi.models.DTO.AutorDTO.AutorCreateDTO;
import com.example.libreriaapi.models.DTO.AutorDTO.AutorDeleteDTO;
import com.example.libreriaapi.models.DTO.AutorDTO.AutorModificarDTO;
import com.example.libreriaapi.servicios.AutorServicio;
import com.example.libreriaapi.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private LibroServicio libroServicio;

    @PostMapping("/crear")
    public ResponseEntity<Object> crearAutor(
            @RequestBody  AutorCreateDTO autorCreateDTO) {
        try {
            autorServicio.registrar(autorCreateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Algun dato no es correcto o es nulo, revisar.\"}");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarAutor() {
        try {
            List<Autor> autorList = autorServicio.listarTodo();
            return ResponseEntity.ok(autorList);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/modificar")
    public ResponseEntity<Void> modificarAutor(
            @RequestBody AutorModificarDTO autorModificarDTO
    ) {
        try {
            autorServicio.actualizar(
                    autorModificarDTO
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/baja")
    public ResponseEntity<Void> bajaAutor(
            @RequestParam String idAutor
    ) {
        try {
            List<Libro> libroList = libroServicio.librosActivosDeAutor(idAutor);
            Optional<Autor> autorOptional = autorServicio.autorporId(idAutor);
            if (autorOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (!libroList.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            autorServicio.darBaja(idAutor);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarAutor(
            @RequestBody AutorDeleteDTO autorDeleteDTO
    ) {
        try {

            autorServicio.eliminarAutor(autorDeleteDTO);

            return ResponseEntity.ok("Autor eliminado");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
