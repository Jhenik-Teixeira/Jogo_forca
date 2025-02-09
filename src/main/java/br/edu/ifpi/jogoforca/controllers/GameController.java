package br.edu.ifpi.jogoforca.controllers;

import br.edu.ifpi.jogoforca.models.Palavra;
import br.edu.ifpi.jogoforca.services.GameService;
import br.edu.ifpi.jogoforca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jogo")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String jogo(@RequestParam(required = false) String nickname, Model model) {
        if (nickname == null || nickname.trim().isEmpty()) {
            return "redirect:/";
        }

        Palavra palavra = gameService.getRandomPalavra();
        model.addAttribute("palavra", palavra);
        model.addAttribute("topScores", usuarioService.getTopScores());
        model.addAttribute("nickname", nickname);
        return "jogo";
    }

    @PostMapping("/salvar-pontuacao")
    @ResponseBody
    public void salvarPontuacao(@RequestParam String nickname, @RequestParam int pontuacao) {
        usuarioService.saveOrUpdateUsuario(nickname, pontuacao);
    }
}
