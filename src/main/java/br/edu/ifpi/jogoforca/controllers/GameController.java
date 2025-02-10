package br.edu.ifpi.jogoforca.controllers;

import br.edu.ifpi.jogoforca.models.Palavra;
import br.edu.ifpi.jogoforca.services.GameService;
import br.edu.ifpi.jogoforca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/nova-palavra")
    @ResponseBody
    public ResponseEntity<Palavra> novaPalavra() {
        Palavra palavra = gameService.getRandomPalavra();
        if (palavra == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(palavra);
    }

    @GetMapping("/ranking")
    public String mostrarRanking(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("topScores", usuarioService.getTopScores());
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "ranking";
    }

    @PostMapping("/salvar-pontuacao")
    @ResponseBody
    public ResponseEntity<String> salvarPontuacao(@RequestParam String nickname, @RequestParam int pontuacao) {
        try {
            usuarioService.saveOrUpdateUsuario(nickname, pontuacao);
            return ResponseEntity.ok("/jogo/ranking?message=Pontuação salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar pontuação: " + e.getMessage());
        }
    }

    @PostMapping("/reiniciar")
    @ResponseBody
    public ResponseEntity<String> reiniciarJogo() {
        gameService.reiniciarJogo();
        return ResponseEntity.ok("Jogo reiniciado");
    }
}
