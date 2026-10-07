# Sistema de Oficina Mecânica

Projeto desenvolvido em Java utilizando:
- Programação Orientada a Objetos (POO)
- Collections (List)
- Exceptions personalizadas
- Enum
- JDBC com MySQL
- Estrutura em camadas

## 📌 Estrutura do Projeto
- `OficinaMecanica.controller.entity` — entidades do sistema (Cliente, Carro, OrdemServico)
- `OficinaMecanica.controller.repository` — acesso ao banco de dados (DAO)
- `OficinaMecanica.controller.service` — regras de negócio
- `exception` — exceções personalizadas
- `sql` — script de criação do banco

## 📌 Funcionalidades
- Cadastro de clients
- Cadastro de car
- Criação de ordens de serviço
- Atualização de status da ordem
- Listagem de ordens de serviço
- Busca de client por CPF
- Busca de car por placa

## 📌 Status da Ordem
- `INICIADO`
- `CANCELADO`
- `FINALIZADO`

## 📌 Como rodar

### Pré-requisitos
- Java 17+
- MySQL rodando (local ou Docker)

### Banco de dados
1. Execute o script `sql/banco.sql` no seu MySQL para criar o banco e as tabelas
2. Configure as credenciais de conexão em `src/connection/Conexao.java`

## 🚀 Melhorias futuras
- Spring Boot
- API REST
- Interface gráfica