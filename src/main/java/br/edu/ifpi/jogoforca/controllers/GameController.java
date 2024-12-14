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
    public String jogo(Model model) {
        Palavra palavra = gameService.getRandomPalavra();
        model.addAttribute("palavra", palavra);
        model.addAttribute("topScores", usuarioService.getTopScores());
        return "jogo";
    }

    @PostMapping("/resultado")
    @ResponseBody
    public void salvarPontuacao(@RequestParam String nickname, @RequestParam int pontuacao) {
        usuarioService.saveOrUpdateUsuario(nickname, pontuacao);
    }
}
