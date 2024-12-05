package com.example.libreriaapi.repositorios;

import com.example.libreriaapi.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("SELECT e FROM Editorial e WHERE e.idEditorial = :idEditorial")
    Editorial editorialPorId(@Param("idEditorial")String idEditorial );
}
