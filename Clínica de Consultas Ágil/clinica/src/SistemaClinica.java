import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaClinica {

    private static final String ARQUIVO_PACIENTES = "pacientes.txt";
    private static final String ARQUIVO_CONSULTAS = "consultas.txt";

    private static ArrayList<Paciente> pacientesCadastrados = new ArrayList<>();
    private static ArrayList<Consulta> consultasAgendadas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carregarPacientes();
        carregarConsultas();

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    marcarConsulta();
                    break;
                case 3:
                    cancelarConsulta();
                    break;
                case 4:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha novamente.");
            }
        } while (opcao != 4);

        salvarConsultas();
    }

    private static void exibirMenu() {
        System.out.println("### Menu Principal ###");
        System.out.println("1. Cadastrar um paciente");
        System.out.println("2. Marcar uma consulta");
        System.out.println("3. Cancelar uma consulta");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarPaciente() {
        System.out.println("### Cadastro de Paciente ###");
        System.out.println("Digite 'voltar' a qualquer momento para retornar ao menu principal.");
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine().trim();

        if (nome.equalsIgnoreCase("voltar")) {
            return;
        }

        System.out.print("Telefone do paciente: ");
        String telefone = scanner.nextLine().trim();

        if (telefone.equalsIgnoreCase("voltar")) {
            return;
        }

        if (Paciente.buscarPacientePorTelefone(pacientesCadastrados, telefone) != null) {
            System.out.println("Paciente já cadastrado!");
        } else {
            Paciente paciente = new Paciente(nome, telefone);
            pacientesCadastrados.add(paciente);
            salvarPacientes();
            System.out.println("Paciente cadastrado com sucesso!");
        }
    }

    private static void marcarConsulta() {
        System.out.println("### Marcação de Consulta ###");
        System.out.println("Digite 'voltar' a qualquer momento para retornar ao menu principal.");

        if (pacientesCadastrados.isEmpty()) {
            System.out.println("Não há pacientes cadastrados para marcar consulta.");
            return;
        }

        System.out.println("Lista de Pacientes:");
        for (int i = 0; i < pacientesCadastrados.size(); i++) {
            Paciente paciente = pacientesCadastrados.get(i);
            System.out.println((i + 1) + ". " + paciente.getNome() + " - " + paciente.getTelefone());
        }

        System.out.print("Escolha o número do paciente para marcar consulta: ");
        String escolha = scanner.nextLine().trim();
        if (escolha.equalsIgnoreCase("voltar")) {
            return;
        }

        int indicePaciente = Integer.parseInt(escolha) - 1;

        if (indicePaciente < 0 || indicePaciente >= pacientesCadastrados.size()) {
            System.out.println("Índice de paciente inválido.");
            return;
        }

        Paciente pacienteSelecionado = pacientesCadastrados.get(indicePaciente);

        System.out.print("Data da consulta (DD/MM/AAAA): ");
        String data = scanner.nextLine().trim();

        if (data.equalsIgnoreCase("voltar")) {
            return;
        }

        if (!Consulta.dataValida(data)) {
            System.out.println("Data inválida. Por favor, insira uma data no formato DD/MM/AAAA.");
            return;
        }

        System.out.print("Hora da consulta (HH:MM): ");
        String hora = scanner.nextLine().trim();

        if (hora.equalsIgnoreCase("voltar")) {
            return;
        }

        if (!Consulta.horaValida(hora)) {
            System.out.println("Hora inválida. Por favor, insira uma hora no formato HH:MM entre 00:00 e 23:59.");
            return;
        }

        System.out.print("Especialidade desejada: ");
        String especialidade = scanner.nextLine().trim();

        if (especialidade.equalsIgnoreCase("voltar")) {
            return;
        }

        if (Consulta.consultaJaAgendada(consultasAgendadas, data, hora)) {
            System.out.println("Já existe uma consulta marcada para esta data e hora.");
            return;
        }

        if (!Consulta.dataFutura(data, obterDataAtual())) {
            System.out.println("Não é possível marcar consultas retroativas.");
            return;
        }

        Consulta consulta = new Consulta(pacienteSelecionado, data, hora, especialidade);
        consultasAgendadas.add(consulta);
        salvarConsultas();
        System.out.println("Consulta marcada com sucesso!");
    }

    private static void cancelarConsulta() {
        System.out.println("### Cancelamento de Consulta ###");
        System.out.println("Digite 'voltar' a qualquer momento para retornar ao menu principal.");

        if (consultasAgendadas.isEmpty()) {
            System.out.println("Não há consultas agendadas para cancelar.");
            return;
        }

        System.out.println("Lista de Consultas Agendadas:");
        for (int i = 0; i < consultasAgendadas.size(); i++) {
            Consulta consulta = consultasAgendadas.get(i);
            System.out.println((i + 1) + ". " + consulta.getPaciente().getNome() + " - " +
                    consulta.getData() + " " + consulta.getHora() + " - " + consulta.getEspecialidade());
        }

        System.out.print("Escolha o número da consulta para cancelar: ");
        String escolha = scanner.nextLine().trim();
        if (escolha.equalsIgnoreCase("voltar")) {
            return;
        }

        int indiceConsulta = Integer.parseInt(escolha) - 1;

        if (indiceConsulta < 0 || indiceConsulta >= consultasAgendadas.size()) {
            System.out.println("Índice de consulta inválido.");
            return;
        }

        Consulta consultaCancelada = consultasAgendadas.get(indiceConsulta);
        System.out.println("Consulta selecionada para cancelamento:");
        System.out.println(consultaCancelada.getPaciente().getNome() + " - " +
                consultaCancelada.getData() + " " + consultaCancelada.getHora() + " - "
                + consultaCancelada.getEspecialidade());

        System.out.print("Confirmar o cancelamento (S/N)? ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equalsIgnoreCase("voltar")) {
            return;
        }

        if (confirmacao.equals("S")) {
            consultasAgendadas.remove(indiceConsulta);
            salvarConsultas();
            System.out.println("Consulta cancelada com sucesso.");
        } else {
            System.out.println("Cancelamento de consulta abortado.");
        }
    }

    private static String obterDataAtual() {
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return hoje.format(formatter);
    }

    private static void salvarPacientes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_PACIENTES))) {
            for (Paciente paciente : pacientesCadastrados) {
                writer.println(paciente.getNome() + ";" + paciente.getTelefone());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    private static void carregarPacientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PACIENTES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    Paciente paciente = new Paciente(partes[0], partes[1]);
                    pacientesCadastrados.add(paciente);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar pacientes: " + e.getMessage());
        }
    }

    private static void salvarConsultas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_CONSULTAS))) {
            for (Consulta consulta : consultasAgendadas) {
                writer.println(consulta.getPaciente().getNome() + ";" +
                        consulta.getPaciente().getTelefone() + ";" +
                        consulta.getData() + ";" +
                        consulta.getHora() + ";" +
                        consulta.getEspecialidade());
            }
            System.out.println("Consultas salvas com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar Consultas: " + e.getMessage());
        }
    }

    private static void carregarConsultas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONSULTAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    String nome = partes[0];
                    String telefone = partes[1];
                    String data = partes[2];
                    String hora = partes[3];
                    String especialidade = partes[4];

                    Paciente paciente = Paciente.buscarPacientePorTelefone(pacientesCadastrados, telefone);
                    if (paciente != null) {
                        Consulta consulta = new Consulta(paciente, data, hora, especialidade);
                        consultasAgendadas.add(consulta);
                    } else {
                        System.out.println("Paciente não encontrado para a consulta: " + linha);
                    }
                } else {
                    System.out.println("Formato inválido da linha: " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar consultas: " + e.getMessage());
        }
    }

}