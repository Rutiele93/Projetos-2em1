import java.io.Serializable;
import java.util.ArrayList;

public class Paciente implements Serializable {
    private String nome;
    private String telefone;

    public Paciente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public static Paciente buscarPacientePorTelefone(ArrayList<Paciente> pacientes, String telefone) {
        for (Paciente paciente : pacientes) {
            if (paciente.getTelefone().equals(telefone)) {
                return paciente;
            }
        }
        return null;
    }
}
