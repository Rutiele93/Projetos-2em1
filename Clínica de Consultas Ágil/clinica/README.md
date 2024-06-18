# Clínica de Consultas Ágil
Este projeto implementa um sistema de clínica de consultas em um ambiente de terminal. A seguir, detalharei as tecnologias utilizadas e como o sistema foi estruturado para atender aos requisitos propostos.

## Tecnologias Utilizadas
- **Linguagem de Programação:** Java
- **Manipulação de Arquivos:** Operações de leitura e escrita para persistência dos dados utilizando `FileReader`, `BufferedReader`, `FileWriter` e `PrintWriter`.
- **Manipulação de Datas:** Utilização da classe LocalDate e DateTimeFormatter para validação e formatação de datas.
- **Estrutura de Dados:** Utilização de ArrayList para armazenar e manipular listas de pacientes cadastrados e consultas agendadas.
- **Entrada de Dados:** Utilização da classe Scanner para interação com o usuário via terminal.

## Funcionalidades Implementadas
#### 1. Cadastro de Pacientes
O usuário pode cadastrar um novo paciente fornecendo nome e telefone. O sistema verifica se o número de telefone já está cadastrado para evitar duplicidade.

#### 2. Marcação de Consultas
O usuário pode agendar consultas selecionando um paciente cadastrado, informando data, hora e especialidade desejada. O sistema verifica se a data e hora escolhidas estão disponíveis e se são futuras.

#### 3. Cancelamento de Consultas
O usuário pode cancelar uma consulta previamente agendada, escolhendo-a a partir de uma lista numerada de consultas. O sistema exibe detalhes da consulta selecionada e solicita confirmação antes de efetuar o cancelamento.

#### 4. Tratamento de Erros
Evita duplicidade de pacientes pelo número de telefone.
Garante que não seja possível agendar consultas em datas e horários já ocupados.
Impede o agendamento de consultas retroativas.

#### 5. Persistência de Dados
Os dados de pacientes e consultas são armazenados em arquivos de texto (pacientes.txt e consultas.txt). Isso permite que as informações persistam entre diferentes execuções do programa.

### Estrutura do Projeto

- #### Classes Principais:

- SistemaClinica: Contém o método main e implementa o menu principal, além de gerenciar as interações com o usuário.
- Paciente: Representa um paciente com atributos de nome e telefone, métodos para buscar e verificar duplicidade.
- Consulta: Representa uma consulta com informações de paciente, data, hora e especialidade, além de métodos para validação e verificação de disponibilidade.
  
- #### Persistência de Dados:
- **Métodos ´salvarPacientes()´ e ´carregarPacientes()´:** Para persistência dos pacientes cadastrados.
- **Métodos ´salvarConsultas()´ e ´carregarConsultas()´:** Para persistência das consultas agendadas.

### Como Executar o Projeto
1. Clone o repositório para sua máquina local.
2. Compile e execute o programa através de um ambiente de desenvolvimento Java compatível.
3. Siga as instruções apresentadas no terminal para interagir com o sistema.

### Conclusão
Este projeto proporciona uma solução simples e eficaz para gerenciar consultas médicas em uma clínica, utilizando conceitos básicos de programação orientada a objetos e manipulação de arquivos em Java. A persistência dos dados garante que as informações dos pacientes e consultas sejam mantidas entre sessões do sistema, proporcionando uma experiência contínua para os usuários.
