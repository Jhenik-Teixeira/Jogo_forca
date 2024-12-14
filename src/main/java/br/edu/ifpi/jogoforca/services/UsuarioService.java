package br.edu.ifpi.jogoforca.services;

import br.edu.ifpi.jogoforca.models.Usuario;
import br.edu.ifpi.jogoforca.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario saveOrUpdateUsuario(String nickname, int pontuacao) {
        Usuario usuario = usuarioRepository.findByNickname(nickname)
                .orElse(new Usuario());
        usuario.setNickname(nickname);
        if (pontuacao > usuario.getPontuacaoMaxima()) {
            usuario.setPontuacaoMaxima(pontuacao);
            usuario.setDataMelhorPontuacao(LocalDateTime.now());
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getTopScores() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.DESC, "pontuacaoMaxima"))
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }
}
