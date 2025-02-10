package br.edu.ifpi.jogoforca.services;

import br.edu.ifpi.jogoforca.models.Palavra;
import br.edu.ifpi.jogoforca.repositories.PalavraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GameService {
    @Autowired
    private PalavraRepository palavraRepository;
    
    // Guarda as palavras já usadas na sessão atual
    private Set<Long> palavrasUsadas = new HashSet<>();

    public Palavra getRandomPalavra() {
        Palavra palavra;
        int tentativas = 0;
        int maxTentativas = 10; // Evita loop infinito se houver poucas palavras

        do {
            palavra = palavraRepository.findRandomPalavra();
            tentativas++;
            
            // Se todas as palavras foram usadas ou muitas tentativas, reinicia
            if (tentativas >= maxTentativas) {
                palavrasUsadas.clear();
                break;
            }
        } while (palavra != null && palavrasUsadas.contains(palavra.getId()));

        if (palavra != null) {
            palavrasUsadas.add(palavra.getId());
        }

        return palavra;
    }

    // Método para reiniciar o jogo (limpar palavras usadas)
    public void reiniciarJogo() {
        palavrasUsadas.clear();
    }
}
