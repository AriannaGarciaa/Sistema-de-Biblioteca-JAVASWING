package Repository;

import Model.EmprestimoModel;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepository {
    private List<EmprestimoModel> emprestimos = new ArrayList<>();

    // Salvar empréstimo
    public void salvar(EmprestimoModel emprestimo) {
        emprestimos.add(emprestimo);
    }

    // Listar todos os empréstimos
    public List<EmprestimoModel> listar() {
        return emprestimos;
    }

    // Buscar empréstimos por ID do usuário
    public List<EmprestimoModel> buscarPorUsuarioId(int usuarioId) {
        List<EmprestimoModel> resultado = new ArrayList<>();
        for (EmprestimoModel e : emprestimos) {
            if (e.getUsuarioId() == usuarioId) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    // Atualizar status de devolução
    public void atualizarDevolucao(int id, boolean devolvido) {
        for (EmprestimoModel e : emprestimos) {
            if (e.getId() == id) {
                e.setDevolvido(devolvido);
            }
        }
    }
}
