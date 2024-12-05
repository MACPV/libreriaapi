package com.example.libreriaapi.servicios;

import com.example.libreriaapi.entidades.Autor;
import com.example.libreriaapi.entidades.Editorial;
import com.example.libreriaapi.entidades.Libro;
import com.example.libreriaapi.models.DTO.LibroDTO.LibroCreateDTO;
import com.example.libreriaapi.models.DTO.LibroDTO.LibroDeleteDTO;
import com.example.libreriaapi.models.DTO.LibroDTO.LibroListarActivosDTO;
import com.example.libreriaapi.models.DTO.LibroDTO.LibroModificarDTO;
import com.example.libreriaapi.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;


    @Transactional
    public void crearLibro(LibroCreateDTO libroCreateDTO) {

        Libro libronvo = new Libro();
        libronvo.setIdLibro(libroCreateDTO.getIsbn());
        libronvo.setTitulo(libroCreateDTO.getTitulo());
        libronvo.setEjemplares(libroCreateDTO.getEjemplares());
        libronvo.setLibroActivo(libroCreateDTO.isLibroActivo());

        Autor autor = autorServicio.getOne(libroCreateDTO.getIdAutor());
        if (autor != null) {
            libronvo.setAutor(autor);
        }

        Editorial editorial = editorialServicio.getOne(libroCreateDTO.getIdEditorial());
        if (editorial != null) {
            libronvo.setEditorial(editorial);
        }
        libroRepositorio.save(libronvo);
    }

    @Transactional
    public void modificarLibro(LibroModificarDTO libroModificarDTO) {
        Optional<Libro> libroOptional = libroRepositorio.findById(libroModificarDTO.getIdLibro());

        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();

            libro.setTitulo(libroModificarDTO.getTitulo());
            libro.setEjemplares(libroModificarDTO.getEjemplares());

            Editorial editorial = editorialServicio.getOne(libroModificarDTO.getIdEditorial());
            libro.setEditorial(editorial);

            Autor autor = autorServicio.getOne(libroModificarDTO.getIdAutor());
            libro.setAutor(autor);

            libroRepositorio.save(libro);
        }


    }

    @Transactional(readOnly = true)
    public List<LibroListarActivosDTO> librosActivos() {
        return libroRepositorio.encontrarActivos();
    }

    @Transactional(readOnly = true)
    public List<Libro> librosActivosDeAutor(String idAutor) {
        return libroRepositorio.libroconAutor(idAutor);
    }

    @Transactional(readOnly = true)
    public List<Libro> libroActivoDeEditorial(String idEditorial) {
        return  libroRepositorio.libroconEditorial(idEditorial);
    }

    @Transactional
    public void darBaja(Integer idLibro) {
        Optional<Libro> libroOptional = libroRepositorio.findById(idLibro);
        if (libroOptional.isPresent()) {

            Libro libro = libroOptional.get();
            libro.setLibroActivo(false);

            libroRepositorio.save(libro);

        }
    }

    @Transactional
    public void eliminarLibro(LibroDeleteDTO libroDeleteDTO){

    }
}
