import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Consulta implements Serializable {
    private Paciente paciente;
    private String data;
    private String hora;
    private String especialidade;

    public Consulta(Paciente paciente, String data, String hora, String especialidade) {
        this.paciente = paciente;
        this.data = data;
        this.hora = hora;
        this.especialidade = especialidade;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public static boolean dataValida(String data) {
        if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", data)) {
            return false;
        }

        String[] partesData = data.split("/");
        int dia = Integer.parseInt(partesData[0]);
        int mes = Integer.parseInt(partesData[1]);
        int ano = Integer.parseInt(partesData[2]);

        if (mes < 1 || mes > 12) {
            return false;
        }

        int[] diasNoMes = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) {
            diasNoMes[1] = 29; // Ano bissexto
        }

        return dia > 0 && dia <= diasNoMes[mes - 1];
    }

    public static boolean horaValida(String hora) {
        String[] partesHora = hora.split(":");
        if (partesHora.length != 2) {
            return false;
        }

        try {
            int horas = Integer.parseInt(partesHora[0]);
            int minutos = Integer.parseInt(partesHora[1]);

            if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean consultaJaAgendada(ArrayList<Consulta> consultas, String data, String hora) {
        for (Consulta consulta : consultas) {
            if (consulta.getData().equals(data) && consulta.getHora().equals(hora)) {
                return true;
            }
        }
        return false;
    }

    public static boolean dataFutura(String data, String dataAtual) {
        String[] partesDataAtual = dataAtual.split("/");
        String[] partesDataConsulta = data.split("/");

        int diaAtual = Integer.parseInt(partesDataAtual[0]);
        int mesAtual = Integer.parseInt(partesDataAtual[1]);
        int anoAtual = Integer.parseInt(partesDataAtual[2]);

        int diaConsulta = Integer.parseInt(partesDataConsulta[0]);
        int mesConsulta = Integer.parseInt(partesDataConsulta[1]);
        int anoConsulta = Integer.parseInt(partesDataConsulta[2]);

        if (anoConsulta > anoAtual) {
            return true;
        } else if (anoConsulta == anoAtual) {
            if (mesConsulta > mesAtual) {
                return true;
            } else if (mesConsulta == mesAtual) {
                if (diaConsulta >= diaAtual) {
                    return true;
                }
            }
        }

        return false;
    }
}
