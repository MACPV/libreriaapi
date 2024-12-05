package com.example.libreriaapi.controladores;

import com.example.libreriaapi.entidades.Editorial;
import com.example.libreriaapi.entidades.Libro;
import com.example.libreriaapi.models.DTO.EditorialDTO.EditorialCreateDTO;
import com.example.libreriaapi.models.DTO.EditorialDTO.EditorialDeleteDTO;
import com.example.libreriaapi.models.DTO.EditorialDTO.EditorialModificarDTO;
import com.example.libreriaapi.servicios.EditorialServicio;
import com.example.libreriaapi.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/editorial")
@ResponseStatus
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private LibroServicio libroServicio;

    @PostMapping("/crear")
    public ResponseEntity<Object> crearEditorial(
            @RequestBody EditorialCreateDTO editorialCreateDTO
    ) {
        try {
            editorialServicio.registrar(editorialCreateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Algun dato no es correcto o es nulo, revisar.\"}");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarEditorial() {
        try {
            List<Editorial> editorialList = editorialServicio.listarTodo();
            return ResponseEntity.ok(editorialList);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/baja")
    public ResponseEntity<String> bajaEditorial(
            @RequestParam String idEditorial
    ) {
        try {
            List<Libro> libroList = libroServicio.libroActivoDeEditorial(idEditorial);
            Optional<Editorial> editorialOptional = editorialServicio.buscarPorId(idEditorial);
            if (!libroList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No se puede dar de baja la editorial porque tiene libros activos asociados.");
            }
            if (editorialOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("La editorial no existe.");
            }

            editorialServicio.darBaja(idEditorial);
            return ResponseEntity.ok("La editorial ha sido dada de baja con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado.");
        }
    }

    @PatchMapping("/modificar")
    public ResponseEntity<Void> modificarEditorial(
            @RequestBody EditorialModificarDTO editorialModificarDTO
    ) {
        try {
            editorialServicio.actualizar(editorialModificarDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarEditoria(
            @RequestBody EditorialDeleteDTO editorialDeleteDTO
    ) {
        try {
            editorialServicio.eliminarEditorial(editorialDeleteDTO);
            return ResponseEntity.ok("Editorial eliminada");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listarEditorial/{idEditorial}")
    public ResponseEntity<Editorial> listarEditorial(
            @PathVariable String idEditorial
    ) {
        try {
            Editorial editorial = editorialServicio.getOne(idEditorial);
            return ResponseEntity.ok(editorial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
