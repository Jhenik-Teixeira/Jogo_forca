package br.edu.ifpi.jogoforca.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(nullable = false)
    private int pontuacaoMaxima = 0;  // Inicializa com 0

    @Column(nullable = false)
    private LocalDateTime dataMelhorPontuacao = LocalDateTime.now();  // Inicializa com data atual

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPontuacaoMaxima() {
        return pontuacaoMaxima;
    }

    public void setPontuacaoMaxima(int pontuacaoMaxima) {
        this.pontuacaoMaxima = pontuacaoMaxima;
    }

    public LocalDateTime getDataMelhorPontuacao() {
        return dataMelhorPontuacao;
    }

    public void setDataMelhorPontuacao(LocalDateTime dataMelhorPontuacao) {
        this.dataMelhorPontuacao = dataMelhorPontuacao;
    }

    // Método para atualizar pontuação se for maior que a atual
    public boolean atualizarPontuacao(int novaPontuacao) {
        if (novaPontuacao > this.pontuacaoMaxima) {
            this.pontuacaoMaxima = novaPontuacao;
            this.dataMelhorPontuacao = LocalDateTime.now();
            return true;
        }
        return false;
    }
}
