# 🐾 PetShop - Sistema de Gestão

Sistema de gerenciamento para PetShops desenvolvido em Java, utilizando arquitetura MVC e persistência de dados com SQLite. O projeto foca na aplicação de padrões de projeto (Design Patterns) e integridade de dados.

## 📸 Screenshots

| Tela Principal (Dashboard) | Cadastro de Pets |
|:---:|:---:|
| ![Dashboard](./screenshots/dashboard.png) <br> *Visão geral e log de notificações* | ![Cadastro](./screenshots/cadastro.png) <br> *Cadastro com seleção dinâmica de donos* |

*(Dica: Tire prints do sistema rodando e salve na pasta 'screenshots' do projeto)*

## 🚀 Funcionalidades

- **Gestão de Clientes:** Cadastro, listagem, edição e exclusão (com validação de CPF).
- **Gestão de Pets:** Vínculo automático com donos e controle de espécies.
- **Agendamento de Serviços:** Banho, Tosa, Consultas, etc.
- **Notificações em Tempo Real:** Sistema de logs na tela principal usando o padrão **Observer**.
- **Relatório Financeiro:** Cálculo automático de faturamento de serviços finalizados.
- **Interface Moderna:** Visual aprimorado com a biblioteca **FlatLaf**.

## 🛠️ Tecnologias e Padrões Utilizados

### Tecnologias
- **Java JDK 21+**
- **Swing** (Interface Gráfica)
- **SQLite** (Banco de Dados Relacional)
- [cite_start]**FlatLaf** (Look and Feel moderno) [cite: 331]

### Padrões de Projeto (Design Patterns)
O sistema foi construído seguindo rigorosamente a Orientação a Objetos:
1.  **MVC (Model-View-Controller):** Separação total entre telas, lógica e banco de dados.
2.  **DAO (Data Access Object):** Camada exclusiva para comunicação SQL (`ClienteDAO`, `PetDAO`, `ServicoDAO`).
3.  [cite_start]**Factory Method:** Uso da `FactoryDAO` para instanciar os controladores[cite: 88].
4.  [cite_start]**Observer:** O `ServicoController` notifica a `TelaPrincipal` sempre que um agendamento ou status muda[cite: 49, 300].
5.  **Singleton:** Aplicado na `ConnectionFactory` para garantir uma única conexão com o banco.

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- Java JDK 21 ou superior instalado.
- VS Code ou IntelliJ IDEA.
- Bibliotecas `.jar` necessárias (devem estar no Classpath):
  - `sqlite-jdbc.jar` (Driver do banco)
  - `flatlaf.jar` (Tema visual)

### Passo a Passo
1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/petshop-system.git](https://github.com/seu-usuario/petshop-system.git)# trabalho-petshop-poo
