class JogoDaForca {
    constructor(palavra) {
        this.palavraOriginal = palavra;
        this.palavraAtual = Array(palavra.length).fill('_');
        this.letrasErradas = [];
        this.letrasAcertadas = new Set();
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

        if (this.letrasErradas.includes(letra) || this.letrasAcertadas.has(letra)) {
            alert('Você já tentou esta letra!');
            return;
        }

        let acertou = false;
        if (this.palavraOriginal.toLowerCase().includes(letra)) {
            if (!this.letrasAcertadas.has(letra)) {
                this.letrasAcertadas.add(letra);
                const ocorrencias = this.palavraOriginal.toLowerCase().split(letra).length - 1;
                this.pontuacaoAtual += (10 * ocorrencias);
                acertou = true;
            }

            for (let i = 0; i < this.palavraOriginal.length; i++) {
                if (this.palavraOriginal[i].toLowerCase() === letra) {
                    this.palavraAtual[i] = this.palavraOriginal[i];
                }
            }
        } else {
            this.letrasErradas.push(letra);
            this.tentativasRestantes--;
            
            // Adiciona animação de shake ao errar
            const palavraElement = document.getElementById('palavra');
            palavraElement.classList.remove('shake'); // Remove primeiro para garantir
            void palavraElement.offsetWidth; // Força o reflow
            palavraElement.classList.add('shake');
            
            setTimeout(() => {
                palavraElement.classList.remove('shake');
            }, 500);
            
            document.getElementById('tentativas').textContent = this.tentativasRestantes;
            this.atualizarImagem();
            document.querySelector('#letras-erradas span').textContent = this.letrasErradas.join(', ');
        }

        this.atualizarPalavraExibida();
        this.atualizarPontuacao();
        this.verificarFimDeJogo();
    }
    // Atualiza a palavra exibida e as letras erradas
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
            // Acertou a palavra
            this.pontuacaoAtual += (this.tentativasRestantes * 20);
            this.pontuacaoTotal += this.pontuacaoAtual;
            this.atualizarPontuacao();
            
            setTimeout(() => {
                this.carregarNovaPalavra();
            }, 300);
        } else if (this.tentativasRestantes === 0) {
            // Errou a palavra - fim de jogo
            this.jogoTerminado = true;
            
            // Primeiro, remove as classes caso existam
            const palavraElement = document.getElementById('palavra');
            palavraElement.classList.remove('palavra-revelada');
            palavraElement.classList.remove('shake');
            
            // Força o reflow do DOM
            void palavraElement.offsetWidth;
            
            // Revela a palavra correta
            this.palavraAtual = Array.from(this.palavraOriginal);
            this.atualizarPalavraExibida();
            
            // Adiciona as classes de animação
            palavraElement.classList.add('palavra-revelada');
            palavraElement.classList.add('shake');
            
            // Remove a classe shake após a animação
            setTimeout(() => {
                palavraElement.classList.remove('shake');
            }, 500);
            
            // Mostra mensagem e salva pontuação após as animações
            setTimeout(() => {
                alert(`Game Over! A palavra era: ${this.palavraOriginal}\nPontuação total: ${this.pontuacaoTotal}`);
                this.salvarPontuacao(this.pontuacaoTotal);
            }, 1200); // Aumentado o delay para dar tempo de ver a animação
        }
    }

    carregarNovaPalavra() {
        fetch('/jogo/nova-palavra')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao carregar nova palavra');
                }
                return response.json();
            })
            .then(novaPalavra => {
                if (!novaPalavra || !novaPalavra.palavra) {
                    throw new Error('Palavra inválida recebida do servidor');
                }

                // Atualiza os dados do jogo
                this.palavraOriginal = novaPalavra.palavra;
                this.palavraAtual = Array(novaPalavra.palavra.length).fill('_');
                this.letrasErradas = [];
                this.letrasAcertadas.clear();
                this.tentativasRestantes = 6;
                this.pontuacaoAtual = 0;
                this.jogoTerminado = false;
                
                // Atualiza a dica
                const dicaElement = document.querySelector('.hint-box span');
                if (dicaElement) {
                    dicaElement.textContent = novaPalavra.dica;
                }
                
                // Limpa o input
                const input = document.getElementById('letra-input');
                if (input) {
                    input.value = '';
                }
                
                // Limpa as letras erradas
                const letrasErradasSpan = document.querySelector('#letras-erradas span');
                if (letrasErradasSpan) {
                    letrasErradasSpan.textContent = '';
                }
                
                // Reseta a imagem da forca
                this.atualizarImagem();
                
                // Atualiza a palavra exibida
                this.atualizarPalavraExibida();
                
                // Atualiza tentativas
                const tentativasElement = document.getElementById('tentativas');
                if (tentativasElement) {
                    tentativasElement.textContent = '6';
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao carregar nova palavra. Por favor, recarregue a página.');
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