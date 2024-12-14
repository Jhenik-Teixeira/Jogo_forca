package br.edu.ifpi.jogoforca.services;

import br.edu.ifpi.jogoforca.models.Palavra;
import br.edu.ifpi.jogoforca.repositories.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    private PalavraRepository palavraRepository;

    public Palavra getRandomPalavra() {
        return palavraRepository.findRandomPalavra();
    }
}
