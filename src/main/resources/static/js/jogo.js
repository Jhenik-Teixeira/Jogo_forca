class JogoDaForca {
    constructor(palavra) {
        this.palavraOriginal = palavra;
        this.palavraAtual = Array(palavra.length).fill('_');
        this.letrasErradas = [];
        this.tentativasRestantes = 6;
        this.jogoTerminado = false;
        this.pontuacaoAtual = 0;
        this.pontuacaoTotal = 0;
    }

    inicializar() {
        this.atualizarPalavraExibida();
        this.atualizarPontuacao();
        this.atualizarImagem();
        
        document.getElementById('letra-input').addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.tentarLetra();
            }
        });
    }

    atualizarImagem() {
        const erros = 6 - this.tentativasRestantes;
        document.getElementById('forca-img').src = `/images/forca${erros}.png`;
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
            this.atualizarImagem();
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
        document.getElementById('pontuacao-total').textContent = this.pontuacaoTotal;
    }

    verificarFimDeJogo() {
        if (!this.palavraAtual.includes('_')) {
            this.pontuacaoAtual += (this.tentativasRestantes * 20);
            this.pontuacaoTotal += this.pontuacaoAtual;
            this.atualizarPontuacao();
            
            this.carregarNovaPalavra();
        } else if (this.tentativasRestantes === 0) {
            this.jogoTerminado = true;
            alert(`Game Over! A palavra era: ${this.palavraOriginal}\nPontuação total: ${this.pontuacaoTotal}`);
            this.salvarPontuacao(this.pontuacaoTotal);
        }
    }

    carregarNovaPalavra() {
        fetch('/jogo/nova-palavra')
            .then(response => response.json())
            .then(novaPalavra => {
                this.palavraOriginal = novaPalavra.palavra;
                this.palavraAtual = Array(novaPalavra.palavra.length).fill('_');
                this.letrasErradas = [];
                this.tentativasRestantes = 6;
                this.pontuacaoAtual = 0;
                
                document.querySelector('h2').innerHTML = `Dica: <span>${novaPalavra.dica}</span>`;
                
                this.atualizarPalavraExibida();
                this.atualizarImagem();
                document.getElementById('tentativas').textContent = '6';
                document.querySelector('#letras-erradas span').textContent = '';
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao carregar nova palavra');
                this.jogoTerminado = true;
                this.salvarPontuacao(this.pontuacaoTotal);
            });
    }

    salvarPontuacao(pontos) {
        if (!this.jogoTerminado) return;

        const nickname = document.getElementById('nickname').value;
        
        fetch('/jogo/salvar-pontuacao', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `nickname=${encodeURIComponent(nickname)}&pontuacao=${pontos}`
        })
        .then(response => response.text())
        .then(url => {
            if (url.startsWith('/')) {
                window.location.href = url;  // Redireciona para o ranking
            } else {
                alert('Erro ao salvar pontuação');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao salvar pontuação');
        });
    }

    finalizarJogo(vitoria) {
        this.jogoTerminado = true;
        if (vitoria) {
            alert(`Parabéns! Você finalizou o jogo com ${this.pontuacaoTotal} pontos!`);
        } else {
            alert(`Fim de jogo! Sua pontuação total foi: ${this.pontuacaoTotal}`);
        }
        this.salvarPontuacao(this.pontuacaoTotal);
    }
} 