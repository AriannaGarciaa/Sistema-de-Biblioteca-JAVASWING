package Repository;

import Model.EmprestimoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class EmprestimoRepository {
    private EntityManager em;

    public EmprestimoRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    // Métodos CRUD
    public void salvar(EmprestimoModel emprestimo) {
        em.getTransaction().begin();
        em.persist(emprestimo);
        em.getTransaction().commit();
<<<<<<< HEAD
    }

    public EmprestimoModel buscarPorId(int id) {
        return em.find(EmprestimoModel.class, id);
    }

    // Método para listar todos os empréstimos cadastrados
    public List<EmprestimoModel> listarTodos() {
        return em.createQuery("FROM EmprestimoModel", EmprestimoModel.class).getResultList();
    }

    public void atualizar(EmprestimoModel emprestimo) {
        em.getTransaction().begin();
        em.merge(emprestimo);
        em.getTransaction().commit();
    }

=======
    }

    public EmprestimoModel buscarPorId(int id) {
        return em.find(EmprestimoModel.class, id);
    }

    public List<EmprestimoModel> listar() {
        return em.createQuery("FROM EmprestimoModel", EmprestimoModel.class).getResultList();
    }

    public void atualizar(EmprestimoModel emprestimo) {
        em.getTransaction().begin();
        em.merge(emprestimo);
        em.getTransaction().commit();
    }

>>>>>>> origin/main
    // Método para atualizar devolução
    public void atualizarDevolucao(int id, boolean devolvido) {
        EmprestimoModel emprestimo = buscarPorId(id);
        if (emprestimo != null) {
            emprestimo.setDevolvido(devolvido);
            atualizar(emprestimo);
        }
    }

    // Método para editar o empréstimo (pode ser usado para atualizar os campos do empréstimo)
    public void editar(EmprestimoModel emprestimo) {
        em.getTransaction().begin();
        em.merge(emprestimo);  // Atualiza os dados do empréstimo no banco de dados
        em.getTransaction().commit();
    }
}
