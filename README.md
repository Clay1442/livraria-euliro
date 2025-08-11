# livraria-euliro
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Clay1442/livraria-euliro/blob/main/LICENSE) 

# Sobre o projeto
https://livraria-euliro.vercel.app

Livraria-euliro é um projeto full stack web e mobile desenvolvido de forma independente com objetivo de praticar os conhecimentos de mapeamento de objetos relaciais (ORM) em java, além de desenvolver um website capaz de 
fazer e receber requisições, matendo os dados dos usúarios protegidos com a implantação do modelo de cripitografia JWT

O projeto simula uma loja de livros online, com áreas distintas e seguras para clientes e administradores, utilizando um sistema de autenticação e autorização baseado em Tokens JWT.

## 👤 Cliente
Autenticação: Sistema completo de registro e login.

Navegação: Visualizar o catálogo de livros e ver os detalhes de cada produto.

Carrinho de Compras: Adicionar, remover e atualizar a quantidade de itens no carrinho de forma interativa.

Checkout: Finalizar a compra, convertendo o carrinho em um pedido permanente no sistema.

Área Pessoal: Acessar um painel seguro para visualizar o histórico de pedidos e alterar a senha.

## 👑 Administrador
Gerenciamento de Catálogo: Operações de CRUD (Criar, Ler, Atualizar e Deletar) completas para Livros e Autores.

Controle de Estoque: Funcionalidades específicas para adicionar ou remover unidades do estoque.

Gerenciamento de Usuários: Visualizar a lista de todos os usuários cadastrados e gerenciar seus papéis e permissões (promover um cliente a administrador, por exemplo).

## Layout mobile



## Layout web





## Modelo conceitual
<img width="1254" height="732" alt="image" src="https://github.com/user-attachments/assets/5263c465-ad48-4c17-b79a-df4af6bc90d0" />

## Banco de dados 
<img width="1288" height="635" alt="image" src="https://github.com/user-attachments/assets/4f409ba6-206c-4055-890e-792e7c1300e8" />



# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- JWT
## Front end
- HTML / CSS / JS / React
- Biblioteca Principal: React
- Linguagem: JavaScript (com JSX)
- Build Tool: Vite
- Roteamento: React Router DOM
- Cliente HTTP: Axios
- Estilização: CSS puro
## Implantação em produção
- Back end: Render
- Front end web: Vercel
- Banco de dados: Postgresql

# Como executar o projeto

## Back end
Pré-requisitos:

Java 17

Um cliente de banco de dados como DBeaver ou pgAdmin (Opcional, para visualizar o banco).

Git


Docker (Recomendado, para rodar o PostgreSQL).

```bash
# Clone este repositório
git clone https://github.com/Clay1442/livraria-euliro.git

# Navegue até a pasta raiz do projeto
cd livraria-euliro

# Inicie o banco de dados PostgreSQL com Docker
# (Use a senha que você configurou)
docker run --name euliro-postgres -e POSTGRES_USER=euliro -e POSTGRES_PASSWORD=SUA_SENHA -p 5432:5432 -d postgres

# Navegue para a pasta backend\livraria
cd backend\livraria

# Execute a aplicação (ela iniciará na porta 8080)
./mvnw spring-boot:run

```

## Populando o Banco de Dados (Seed)
Importante: Este processo é necessário apenas na primeira vez que você configura o projeto para popular 
o banco de dados PostgreSQL com usuários, livros e autores de exemplo.

```bash

# Navegue até o arquivo de configuração principal do backend:
backend/livraria/src/main/resources/application.properties

# Mude a linha para ativar os perfis de produção e de teste:
spring.profiles.active=prod,test

# Navegue para a pasta do backend:
cd backend

Execute a aplicação uma única vez para criar as tabelas e inserir os dados:
./mvnw spring-boot:run

# Após a aplicação iniciar com sucesso, pare o servidor (pressionando Ctrl + C no terminal).
```
Configuração para Uso Normal
```bash
# Volte ao arquivo backend/livraria/src/main/resources/application.properties
# Mude a linha de volta para usar apenas o perfil de produção:
spring.profiles.active=prod
# Salve o arquivo. Agora, a aplicação está pronta para o uso normal e não tentará inserir os dados de teste novamente.
```
Após isso você tera um novo usuário Administrador com dados para login: 

email: euliro@gmail.com 

senha: 654321 

Com esse úsuario você pode adicionar livros e mudar papéis de novos usuários 

## Front end 
Pré-requisitos: 

Node.js (que inclui o npm)

```bash
# Abra um NOVO terminal.

# Navegue até a pasta raiz do projeto (se não estiver lá)
cd livraria-euliro

# Navegue para a pasta do frontend
cd frontend

# Instale as dependências do projeto
npm install

# Execute a aplicação (ela iniciará na porta 5173)
npm run dev
```

# Autor

Clay José Ribeiro Soares

www.linkedin.com/in/clayjose
