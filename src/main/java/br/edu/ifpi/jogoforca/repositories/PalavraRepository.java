package br.edu.ifpi.jogoforca.repositories;

import br.edu.ifpi.jogoforca.models.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PalavraRepository extends JpaRepository<Palavra, Long> {

    // A consulta agora limita os resultados a apenas um
    @Query("SELECT p FROM Palavra p ORDER BY RAND()")
    Palavra findRandomPalavra(); // Já estava correto para retornar uma palavra aleatória
}
