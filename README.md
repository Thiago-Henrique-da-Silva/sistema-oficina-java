# Sistema de Oficina Mecânica

Projeto desenvolvido em Java utilizando:
- Programação Orientada a Objetos (POO)
- Collections (List)
- Exceptions personalizadas
- Enum
- JDBC com MySQL
- Estrutura em camadas

## 📌 Estrutura do Projeto
- `domain` — entidades do sistema (Cliente, Carro, OrdemServico)
- `repository` — acesso ao banco de dados (DAO)
- `service` — regras de negócio
- `exception` — exceções personalizadas
- `sql` — script de criação do banco

## 📌 Funcionalidades
- Cadastro de clientes
- Cadastro de carros
- Criação de ordens de serviço
- Atualização de status da ordem
- Listagem de ordens de serviço
- Busca de cliente por CPF
- Busca de carro por placa

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