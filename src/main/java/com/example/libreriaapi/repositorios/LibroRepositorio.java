package com.example.libreriaapi.repositorios;

import com.example.libreriaapi.entidades.Libro;
import com.example.libreriaapi.models.DTO.LibroDTO.LibroListarActivosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {

    @Query("SELECT new com.example.libreriaapi.models.DTO.LibroDTO.LibroListarActivosDTO(l.idLibro, l.titulo, l.ejemplares) " +
            "FROM Libro l WHERE l.libroActivo = true")
    List<LibroListarActivosDTO> encontrarActivos();

    @Query("SELECT l FROM Libro l WHERE l.libroActivo = true AND l.autor.idAutor = :idAutor " )
    List<Libro> libroconAutor( @Param("idAutor") String idAutor);

    @Query("SELECT l FROM Libro l WHERE l.libroActivo = true AND l.editorial.idEditorial = :idEditorial " )
    List<Libro> libroconEditorial( @Param("idEditorial") String idEditorial);
}
