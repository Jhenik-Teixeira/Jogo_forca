package br.edu.ifpi.jogoforca.services;

import br.edu.ifpi.jogoforca.models.Palavra;
import br.edu.ifpi.jogoforca.repositories.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PalavraService {

    @Autowired
    private PalavraRepository palavraRepository;

    // Retorna todas as palavras
    public List<Palavra> findAll() {
        return palavraRepository.findAll();
    }

    // Busca uma palavra aleatória utilizando o repositório
    public Optional<Palavra> getRandomPalavra() {
        return Optional.ofNullable(palavraRepository.findRandomPalavra());
    }

    // Salvar uma palavra
    public Palavra save(Palavra palavra) {
        return palavraRepository.save(palavra);
    }
}
