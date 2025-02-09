package br.edu.ifpi.jogoforca.repositories;

import br.edu.ifpi.jogoforca.models.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PalavraRepository extends JpaRepository<Palavra, Long> {

    // Modificando a query para retornar apenas uma palavra aleatória
    @Query(value = "SELECT * FROM palavra ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Palavra findRandomPalavra();
}
