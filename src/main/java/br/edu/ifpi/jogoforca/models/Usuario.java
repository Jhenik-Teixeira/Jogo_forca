package br.edu.ifpi.jogoforca.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private int pontuacaoMaxima;
    private LocalDateTime dataMelhorPontuacao;

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
}
