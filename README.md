
# Vellum - Biblioteca Digital de Manhwas

Projeto do Desafio Extra (CRUD com Integração) da disciplina de Programação Orientada a Objetos (professor Talles).

Autora: Maria de Jesus Silva dos Santos

## Sobre o projeto

O Vellum é uma biblioteca digital fictícia de manhwas, com tema de fantasia/RPG, executada no terminal. Leitores se registram na guilda, manhwas são cadastrados no acervo e cada leitor pode guardar seus manhwas favoritos.

## Funcionalidades

- CRUD completo de **Leitores** (nome, email, CEP e endereço).
- CRUD completo de **Manhwas** (título, autor, gênero, sinopse, quantidade de capítulos e status).
- Relação entre as entidades: **Favoritos** (um leitor pode favoritar vários manhwas).
- Consulta de endereço pelo CEP usando a API pública **ViaCEP**.
- Menu interativo com `Scanner` e validação das entradas.

## Tecnologias

- Java 21
- Maven
- MySQL 8 (acesso via JDBC)
- mysql-connector-j, dotenv-java e Gson (declaradas no `pom.xml`)

## Requisitos para rodar

1. JDK 21 instalado.
2. MySQL instalado e **ligado** na porta `3306`, com o usuário `root` **sem senha**.
3. Conexão com a internet (para a consulta de CEP).

## Como executar

1. Baixe ou clone este repositório.
2. Abra a pasta do projeto no IntelliJ IDEA e aguarde o Maven baixar as dependências.
3. Copie o arquivo `.env.example` e renomeie a cópia para `.env`.
4. Execute a classe `br.edu.poo.m0013.Main`.

Não é necessário criar o banco ou as tabelas manualmente. O programa cria o banco `vellum` pela configuração JDBC e inicializa automaticamente as tabelas `leitor`, `manhwa` e `favorito` ao iniciar.

## Configuração (arquivo .env)

As configurações ficam no arquivo `.env`, na raiz do projeto. O arquivo `.env.example` contém os valores padrão para execução.

- `DB_URL`: endereço do banco de dados.
- `DB_USER`: usuário do MySQL (`root`).
- `DB_PASSWORD`: senha do MySQL (vazia).
- `API_VIACEP_URL`: endereço base da API ViaCEP.

Se o MySQL da sua máquina utilizar outro usuário ou senha, ajuste `DB_USER` e `DB_PASSWORD` no `.env`.

**Importante:** o arquivo `.env` contém configurações locais e não deve ser enviado ao repositório público.

## Estrutura do projeto

- `api`: integração com a API ViaCEP.
- `dao`: acesso ao banco (`LeitorDAO`, `ManhwaDAO`, `FavoritoDAO`).
- `database`: conexão com o MySQL e inicialização das tabelas (`ConexaoComBanco`, `InicializadorBanco`).
- `model`: entidades (`Leitor`, `Manhwa`, `StatusManhwa`).
- `service`: regras de negócio do sistema.
- `Main.java`: menu interativo e ponto de entrada da aplicação.

## Problemas comuns

- **"Não foi possível conectar ao MySQL"**: confira se o MySQL está ligado, se a porta é `3306` e se o usuário e a senha do `.env` estão corretos.
- **Erro ao consultar o CEP**: verifique sua conexão com a internet e se a API ViaCEP está disponível.
