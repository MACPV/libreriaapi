package com.example.libreriaapi.servicios;

import com.example.libreriaapi.entidades.Editorial;
import com.example.libreriaapi.models.DTO.EditorialDTO.EditorialCreateDTO;
import com.example.libreriaapi.models.DTO.EditorialDTO.EditorialDeleteDTO;
import com.example.libreriaapi.models.DTO.EditorialDTO.EditorialModificarDTO;
import com.example.libreriaapi.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void registrar(
            EditorialCreateDTO editorialCreateDTO
    ) {
        Editorial editorial = new Editorial();
        editorial.setNombreEditorial(editorialCreateDTO.getNombreEditorial());
        editorial.setEditorialActiva(editorialCreateDTO.isEditorialActiva());

        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarTodo() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void actualizar(
            EditorialModificarDTO editorialModificarDTO
    ) {
        Optional<Editorial> editorialOptional = editorialRepositorio.findById(editorialModificarDTO.getIdEditorial());

        Editorial editorial = editorialOptional.get();

        editorial.setNombreEditorial(editorialModificarDTO.getNombreEditorial());

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void darBaja(
            String idEditorial
    ) {
        Optional<Editorial> editorialOptional = editorialRepositorio.findById(idEditorial);
        if (editorialOptional.isPresent()) {
            Editorial editorial = editorialOptional.get();

            editorial.setEditorialActiva(false);

            editorialRepositorio.save(editorial);
        }
    }

    @Transactional(readOnly = true)
    public Editorial getOne(String id) {
        return editorialRepositorio.getReferenceById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Editorial> buscarPorId(String idEditorial) {
        return editorialRepositorio.findById(idEditorial);
    }

    @Transactional
    public void eliminarEditorial(EditorialDeleteDTO editorialDeleteDTO) {
        Optional<Editorial> editorialOptional = editorialRepositorio.findById(editorialDeleteDTO.getIdEditorial());
        if (editorialOptional.isPresent()) {
            Editorial editorial = editorialOptional.get();
            if (editorial.isEditorialActiva()) {
                throw new IllegalStateException("La editorial aún está activa");
            }
            editorialRepositorio.delete(editorial);
        }
    }
}
