package br.edu.ifpi.jogoforca.controllers;

import br.edu.ifpi.jogoforca.models.Usuario;
import br.edu.ifpi.jogoforca.repositories.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String exibirPaginaInicial() {
        return "index"; // Renderiza a página inicial (index.html)
    }

    @PostMapping("/entrar")
    public String entrar(@RequestParam("nickname") String nickname, Model model) {
        // Busca ou cria o usuário
        Usuario usuario = usuarioRepository.findByNickname(nickname)
                .orElseGet(() -> {
                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setNickname(nickname);
                    return usuarioRepository.save(novoUsuario);
                });

        model.addAttribute("usuario", usuario);
        return "redirect:/jogo?nickname=" + nickname; // Redireciona para a página do jogo
    }
}
