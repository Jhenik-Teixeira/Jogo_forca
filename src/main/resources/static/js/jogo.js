class JogoDaForca {
    constructor(palavra) {
        this.palavraOriginal = palavra;
        this.palavraAtual = Array(palavra.length).fill('_');
        this.letrasErradas = [];
        this.tentativasRestantes = 6;
        this.jogoTerminado = false;
        this.pontuacaoAtual = 0;
    }

    inicializar() {
        this.atualizarPalavraExibida();
        this.atualizarPontuacao();
        
        document.getElementById('letra-input').addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.tentarLetra();
            }
        });
    }

    tentarLetra() {
        if (this.jogoTerminado) return;

        const input = document.getElementById('letra-input');
        const letra = input.value.toLowerCase();
        input.value = '';

        if (!letra.match(/[a-z]/i)) {
            alert('Por favor, digite uma letra válida');
            return;
        }

        if (this.letrasErradas.includes(letra) || this.palavraAtual.includes(letra)) {
            alert('Você já tentou esta letra!');
            return;
        }

        let acertou = false;
        for (let i = 0; i < this.palavraOriginal.length; i++) {
            if (this.palavraOriginal[i].toLowerCase() === letra) {
                this.palavraAtual[i] = this.palavraOriginal[i];
                acertou = true;
                this.pontuacaoAtual += 10;
            }
        }

        if (!acertou) {
            this.letrasErradas.push(letra);
            this.tentativasRestantes--;
            document.getElementById('tentativas').textContent = this.tentativasRestantes;
        }

        this.atualizarPalavraExibida();
        this.atualizarPontuacao();
        this.verificarFimDeJogo();
    }

    atualizarPalavraExibida() {
        document.getElementById('palavra').textContent = this.palavraAtual.join(' ');
        document.querySelector('#letras-erradas span').textContent = this.letrasErradas.join(', ');
    }

    atualizarPontuacao() {
        document.getElementById('pontuacao-atual').textContent = this.pontuacaoAtual;
    }

    verificarFimDeJogo() {
        if (!this.palavraAtual.includes('_')) {
            this.jogoTerminado = true;
            this.pontuacaoAtual += (this.tentativasRestantes * 20);
            this.atualizarPontuacao();
            alert('Parabéns! Você venceu! Pontuação final: ' + this.pontuacaoAtual);
            this.salvarPontuacao();
        } else if (this.tentativasRestantes === 0) {
            this.jogoTerminado = true;
            alert('Game Over! A palavra era: ' + this.palavraOriginal);
            this.salvarPontuacao();
        }
    }

    salvarPontuacao() {
        const nickname = document.getElementById('nickname').value;
        
        fetch('/jogo/salvar-pontuacao', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `nickname=${encodeURIComponent(nickname)}&pontuacao=${this.pontuacaoAtual}`
        })
        .then(response => {
            if (response.ok) {
                alert('Pontuação salva com sucesso!');
                setTimeout(() => {
                    window.location.href = '/?message=Jogo+finalizado!';
                }, 2000);
            } else {
                alert('Erro ao salvar pontuação');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao salvar pontuação');
        });
    }
} 