package com.api.rest.apirestencuestas.model.repositories;

import com.api.rest.apirestencuestas.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta,Long> {
}
