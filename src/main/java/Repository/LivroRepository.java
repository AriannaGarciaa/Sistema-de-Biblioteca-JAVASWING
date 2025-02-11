package Repository;

import Model.LivroModel;
import jakarta.persistence.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LivroRepository {
    private static LivroRepository instance;
    protected EntityManager entityManager;

    // Construtor
    public LivroRepository(EntityManager entityManager) {
        this.entityManager = entityManager; // Use o entityManager passado
    }

    // Criar um livro (CREATE)
    public String salvar(LivroModel livro) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return "Salvo com Sucesso.";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return e.getMessage();
        }
    }

    // Buscar um livro por ID (READ)
    public LivroModel buscarPorId(int id) {
        LivroModel livro = new LivroModel();
        try {
            livro = entityManager.find(LivroModel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livro;
    }

    // Listar todos os livros (READ)
    public List<LivroModel> listarTodos() {
        TypedQuery<LivroModel> query = entityManager.createQuery("SELECT l FROM LivroModel l", LivroModel.class);
        return query.getResultList();
    }

    // Buscar livros disponíveis (quantidadeDisponivel > 0)
    public List<LivroModel> buscarLivrosDisponiveis() {
        try {
            return entityManager.createQuery("SELECT l FROM LivroModel l WHERE l.quantidadeDisponivel > 0", LivroModel.class)
                    .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList(); // Retorna uma lista vazia em caso de erro
        }
    }

    // Atualizar um livro (UPDATE)
    public void atualizar(LivroModel livro) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(livro); // Atualiza o livro se já existe
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    // Deletar um livro por ID (DELETE)
    public void deletar(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            LivroModel livro = entityManager.find(LivroModel.class, id);
            if (livro != null) {
                entityManager.remove(livro);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}