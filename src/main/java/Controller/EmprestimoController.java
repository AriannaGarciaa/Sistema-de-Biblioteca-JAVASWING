package Controller;

import Model.EmprestimoModel;
import Model.UsuarioModel;
import Model.LivroModel;
import Repository.EmprestimoRepository;
import Repository.UsuarioRepository;
import Repository.LivroRepository;
import jakarta.persistence.EntityManager;

import java.util.Date;
import java.util.List;

public class EmprestimoController {
    private EmprestimoRepository emprestimoRepository;
    private UsuarioRepository usuarioRepository;
    private LivroRepository livroRepository;

    public EmprestimoController(EntityManager entityManager) {
        this.emprestimoRepository = new EmprestimoRepository(entityManager);
        this.usuarioRepository = new UsuarioRepository(entityManager);
        this.livroRepository = new LivroRepository(entityManager);
    }

    // Registrar um novo empréstimo
    public void salvar(EmprestimoModel emprestimo) {
        try {
            // Buscar o usuário e o livro pelo ID
            UsuarioModel usuario = usuarioRepository.buscarPorId(emprestimo.getUsuario().getId());
            LivroModel livro = livroRepository.buscarPorId(emprestimo.getLivro().getId());

            // Verificar se o usuário e o livro existem
            if (usuario != null && livro != null) {
                emprestimo.setUsuario(usuario);
                emprestimo.setLivro(livro);
                emprestimo.setDataEmprestimo(new Date());
                emprestimo.setDevolvido(false); // Inicialmente, o livro não está devolvido

                // Definir a data de devolução (14 dias após o empréstimo)
                Date dataDevolucao = new Date();
                dataDevolucao.setTime(dataDevolucao.getTime() + (14L * 24 * 60 * 60 * 1000));
                emprestimo.setDataDevolucao(dataDevolucao);

                // Salvar o empréstimo
                emprestimoRepository.salvar(emprestimo);
                System.out.println("Empréstimo registrado com sucesso!");
            } else {
                System.out.println("Usuário ou livro não encontrados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar todos os empréstimos
    public List<EmprestimoModel> listarEmprestimos() {
        return emprestimoRepository.listarTodos();
    }

    // Devolver um livro
    public void devolverEmprestimo(int emprestimoId) {
        EmprestimoModel emprestimo = emprestimoRepository.buscarPorId(emprestimoId);
        if (emprestimo != null) {
            // Definir que o livro foi devolvido
            emprestimo.setDevolvido(true);

            // Calcular a data de devolução
            Date dataDevolucao = new Date(); // data atual
            emprestimo.setDataDevolucao(dataDevolucao); // atualiza a data de devolução

            // Salvar o empréstimo com a devolução atualizada
            emprestimoRepository.atualizar(emprestimo);
            System.out.println("Livro devolvido com sucesso! Data de devolução: " + dataDevolucao);
        } else {
            System.out.println("Empréstimo não encontrado!");
        }
    }

    // Buscar um empréstimo por ID
    public EmprestimoModel buscarPorId(int id) {
        EmprestimoModel emprestimo = emprestimoRepository.buscarPorId(id);
        if (emprestimo != null) {
            return emprestimo;
        } else {
            System.out.println("Empréstimo não encontrado com ID: " + id);
            return null;
        }
    }
}
