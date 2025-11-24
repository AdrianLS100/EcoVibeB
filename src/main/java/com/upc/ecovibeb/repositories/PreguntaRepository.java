package com.upc.ecovibeb.repositories;

import com.upc.ecovibeb.entities.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    @Query(value = "SELECT * FROM preguntas_quiz ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
    List<Pregunta> findRandomQuestions();
}