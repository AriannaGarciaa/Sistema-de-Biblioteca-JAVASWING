package Repository;

import Model.Emprestimo;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepository {
    private List<Emprestimo> emprestimos = new ArrayList<>();

    // Salvar empréstimo
    public void salvar(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    // Listar todos os empréstimos
    public List<Emprestimo> listar() {
        return emprestimos;
    }

    // Buscar empréstimos por ID do usuário
    public List<Emprestimo> buscarPorUsuarioId(int usuarioId) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getUsuarioId() == usuarioId) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    // Atualizar status de devolução
    public void atualizarDevolucao(int id, boolean devolvido) {
        for (Emprestimo e : emprestimos) {
            if (e.getId() == id) {
                e.setDevolvido(devolvido);
            }
        }
    }
}
