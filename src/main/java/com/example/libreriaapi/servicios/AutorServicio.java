package com.example.libreriaapi.servicios;

import com.example.libreriaapi.entidades.Autor;
import com.example.libreriaapi.models.DTO.AutorDTO.AutorCreateDTO;
import com.example.libreriaapi.models.DTO.AutorDTO.AutorDeleteDTO;
import com.example.libreriaapi.models.DTO.AutorDTO.AutorModificarDTO;
import com.example.libreriaapi.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void registrar(AutorCreateDTO autorCreateDTO) {

        Autor autor = new Autor();

        autor.setNombreAutor(autorCreateDTO.getNombreAutor());
        autor.setAutorActivo(autorCreateDTO.isAutorActivo());

        autorRepositorio.save(autor);

    }

    @Transactional
    public void actualizar(
            AutorModificarDTO autorModificarDTO
    ) {

        Optional<Autor> autorOptional = autorRepositorio.findById(autorModificarDTO.getIdAutor());

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            autor.setNombreAutor(autorModificarDTO.getNombreAutor());

            autorRepositorio.save(autor);
        } else {
            throw new NoSuchElementException("No se encontró el autor con el ID: " + autorModificarDTO.getIdAutor());
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodo() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public void darBaja(
            String idAutor
    ) {
        Optional<Autor> autorOptional = autorRepositorio.findById(idAutor);

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            autor.setAutorActivo(false);

            autorRepositorio.save(autor);
        }
    }

    @Transactional(readOnly = true)
    public Autor getOne(String id) {
        return autorRepositorio.getReferenceById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Autor> autorporId(String id) {
        return autorRepositorio.findById(id);
    }

    @Transactional
    public void eliminarAutor(AutorDeleteDTO autorDeleteDTO) throws Exception {

        Optional<Autor> autorOptional = autorRepositorio.findById(autorDeleteDTO.getIdAutor());
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            if (autor.isAutorActivo()) {
                throw new IllegalStateException("El autor aún está activo. Debe darse de baja antes de eliminarlo.");
            }
            autorRepositorio.delete(autor);
        }

    }
}
