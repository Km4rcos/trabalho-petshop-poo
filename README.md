# 🐾 PetShop Pro - Sistema de Gestão

> **Projeto Final da Disciplina de Programação Orientada a Objetos (POO)**

Este software é uma solução completa para o gerenciamento de PetShops, desenvolvida em **Java** com foco em arquitetura robusta, código limpo e aplicação consciente de padrões de projeto. O sistema utiliza **SQLite** para persistência de dados local e o tema **FlatLaf** para uma interface moderna.

---

## 👨‍🎓 Identificação da Dupla

* **Alunos:** Brenda Cristina Mota Bahia e Antonio Marcos Oliveira da Silva
* **Semestre:** 2025.2
* **Disciplina:** Programação Orientada a Objetos
* **Projeto:** Sistema de Gerenciamento de PetShop com Persistência e MVC

---

## 📝 Descrição e Justificativa

### Descrição do Problema
O gerenciamento manual de PetShops costuma apresentar problemas como perda de histórico de atendimentos, dificuldade em associar pets aos seus donos e falta de controle financeiro sobre os serviços prestados. Este software automatiza esses processos, garantindo a integridade dos dados e agilidade no atendimento.

### Justificativa do Tema
O projeto atende aos quatro pilares da POO (Encapsulamento, Herança, Polimorfismo e Abstração) através de:
1.  **Relacionamentos Reais:** Exploração da relação "1 para N" (Um dono possui vários pets) com integridade referencial.
2.  **Fluxo de Estados:** Controle de status de serviços (Agendado, Atendendo, Finalizado, Cancelado).
3.  **Aplicabilidade:** Cenário real que exige validações robustas (CPF, Máscaras e Tratamento de Exceções).

---

## 🚀 Funcionalidades Obrigatórias (CRUD)

- **Gestão de Clientes:** Cadastro, listagem, edição e exclusão (com validação de CPF e E-mail).
- **Gestão de Pets:** Vínculo com donos e controle de espécies/raças.
- **Agendamento de Serviços:** Controle de Banho, Tosa, Consultas e Vacinas.
- **Relatório Financeiro:** Cálculo automático de faturamento baseado em serviços finalizados.
- **Notificações em Tempo Real:** Log de operações na tela principal via padrão Observer.

---

## 🛠️ Tecnologias e Padrões Arquiteturais

### Tecnologias
- **Linguagem:** Java JDK 21+
- **GUI:** Java Swing com biblioteca **FlatLaf** (Look and Feel).
- **Banco de Dados:** SQLite (Embarcado/Portátil).

### Padrões de Projeto (Design Patterns)
- **MVC (Model-View-Controller):** Separação clara entre View, Controller e Model.
- **DAO (Data Access Object):** Isolamento total das instruções SQL.
- **Factory Method:** Implementado na `FactoryDAO` e `ConnectionFactory`.
- **Observer:** Reatividade na interface; o `ServicoController` notifica a `TelaPrincipal` sobre mudanças.
- **Singleton:** Garantia de instância única na conexão com o banco de dados.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- Java JDK 21 ou superior instalado.
- VS Code ou IntelliJ IDEA.

### Execução
- Execute a classe App.java (localizada no pacote br.com.petshop).
- Ou use o .jar com o comando (java -jar petshop.jar)

### P.S
- O projeto tem o total de 64 commits, sendo 34 realizados por Marcos e 30 por Brenda. Existe uma discrepância com relação as linhas de códigos porque a commit da parte gráfica foi enviada por Marcos e esta possui uma quantidade maior de linhas. Entretanto, ambos participaram da realização integral do projeto pois se reuniram com constância e afinco para que este fosse desenvolvido da melhor maneira possível.

  Atenciosamente.
## 📸 Screenshots
- Tela Principal
![Tela Principal](./screenshots/Tela-Principal-do-Software.png) <br>
- Tela de Cadastro de Cliente
![Tela de Criação de Cliente](./screenshots/Tela-de-Criação-de-Cliente.png) <br>
-Tela de Lista de Cliente
![Tela de Criação de Cliente](./screenshots/Tela-da-Lista-de-Clientes.png) <br>
-Tela Edição de Cliente
![Tela de Criação de Cliente](./screenshots/Tela-Edição-de-Cliente.png) <br>
-Tela de Criação de Pet
![Tela de Criação de Pet](./screenshots/Tela-de-Criação-de-Pet.png) <br>
-Tela de Lista de Pets
![Tela de Lista de Pets](./screenshots/Tela-de-Lista-de-Pets.png) <br>
-Tela de Edição de Pets
![Tela de Edição de Pets](./screenshots/Tela-de-Edição-de-Pets.png) <br>
-Tela de Agendamento de Serviço
![Tela de Agendamento de Serviço](./screenshots/Tela-de-Agendamento-de-Serviço.png) <br>
-Tela de Lista de Serviços
![Tela de Lista de Serviços](./screenshots/Tela-de-Lista-de-Serviços.png) <br>
-Tela de Edição do Serviço
![Tela de Edição do Serviço](./screenshots/Tela-de-Edição-do-Serviço.png) <br>
-Tela de Finalização de Serviço
![Tela de Finalização de Serviço](./screenshots/Tela-de-Finalização-de-Serviço.png) <br>
-Tela do Relatiorio Financeiro
![Tela do Relatiorio Financeiro](./screenshots/Tela-do-Relatiorio-Financeiro.png) <br>
## 📊 Diagrama de Classes (UML)

![Diagrama](./screenshots/diagrama.png) <br>
