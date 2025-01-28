package Controller;

import Model.Emprestimo;
import Repository.EmprestimoRepository;

import java.util.Date;
import java.util.List;

public class EmprestimoController {
    private EmprestimoRepository emprestimoRepository = new EmprestimoRepository();

    // Registrar um novo empréstimo
    public void registrarEmprestimo(int usuarioId, int livroId) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(emprestimoRepository.listar().size() + 1); // ID baseado no tamanho da lista
        emprestimo.setUsuarioId(usuarioId);
        emprestimo.setLivroId(livroId);
        emprestimo.setDataEmprestimo(new Date());
        emprestimo.setDevolvido(false);

        // Data de devolução prevista (14 dias)
        Date dataDevolucao = new Date();
        dataDevolucao.setTime(dataDevolucao.getTime() + (14L * 24 * 60 * 60 * 1000));
        emprestimo.setDataDevolucao(dataDevolucao);

        emprestimoRepository.salvar(emprestimo);
        System.out.println("Empréstimo registrado com sucesso!");
    }

    // Listar todos os empréstimos
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.listar();
    }

    // Devolver um livro
    public void devolverEmprestimo(int emprestimoId) {
        emprestimoRepository.atualizarDevolucao(emprestimoId, true);
        System.out.println("Livro devolvido com sucesso!");
    }
}
