-- Inserir palavras com suas dicas
INSERT INTO palavra (palavra, dica) VALUES ('JAVA', 'Linguagem de programação orientada a objetos');
INSERT INTO palavra (palavra, dica) VALUES ('SPRING', 'Framework Java para desenvolvimento web');
INSERT INTO palavra (palavra, dica) VALUES ('HTML', 'Linguagem de marcação para páginas web');
INSERT INTO palavra (palavra, dica) VALUES ('PYTHON', 'Linguagem conhecida por sua simplicidade');
INSERT INTO palavra (palavra, dica) VALUES ('JAVASCRIPT', 'Linguagem de programação para web');
INSERT INTO palavra (palavra, dica) VALUES ('DATABASE', 'Local onde armazenamos dados');
INSERT INTO palavra (palavra, dica) VALUES ('FRONTEND', 'Parte visual de uma aplicação');
INSERT INTO palavra (palavra, dica) VALUES ('BACKEND', 'Parte do servidor de uma aplicação');
INSERT INTO palavra (palavra, dica) VALUES ('REACT', 'Biblioteca JavaScript para interfaces');
INSERT INTO palavra (palavra, dica) VALUES ('ANGULAR', 'Framework web do Google');
INSERT INTO palavra (palavra, dica) VALUES ('DOCKER', 'Plataforma de containerização');
INSERT INTO palavra (palavra, dica) VALUES ('LINUX', 'Sistema operacional de código aberto');
INSERT INTO palavra (palavra, dica) VALUES ('GITHUB', 'Plataforma de hospedagem de código');
INSERT INTO palavra (palavra, dica) VALUES ('MAVEN', 'Gerenciador de dependências Java');
INSERT INTO palavra (palavra, dica) VALUES ('MYSQL', 'Sistema de gerenciamento de banco de dados');

-- Não precisamos mais inserir usuários aqui, pois eles serão criados durante o jogo
-- A tabela usuario terá:
-- - id (autogerado)
-- - nickname (único)
-- - pontuacaoMaxima (começa com 0)
-- - dataMelhorPontuacao (atualizada quando bater recorde)
