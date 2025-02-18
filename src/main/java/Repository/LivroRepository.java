package Repository;

import Model.LivroModel;
import jakarta.persistence.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LivroRepository {
    protected EntityManager entityManager;

    // Construtor
    public LivroRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Criar um livro (CREATE)
    public String salvar(LivroModel livro) throws SQLException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(livro);
            transaction.commit();
            return "Livro salvo com sucesso.";
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Erro ao salvar o livro: " + e.getMessage();
        }
    }

    // Buscar um livro por ID (READ)
    public LivroModel buscarPorId(int id) {
        try {
            return entityManager.find(LivroModel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Listar todos os livros (READ)
    public List<LivroModel> listarTodos() {
        try {
            TypedQuery<LivroModel> query = entityManager.createQuery("SELECT l FROM LivroModel l", LivroModel.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Buscar livros disponíveis (quantidadeDisponivel > 0)
    public List<LivroModel> buscarLivrosDisponiveis() {
        try {
            return entityManager.createQuery(
                            "SELECT l FROM LivroModel l WHERE l.quantidadeDisponivel > 0", LivroModel.class)
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
            entityManager.merge(livro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
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
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Buscar livro por nome (READ)
    public LivroModel buscarPorNome(String livroNome) {
        try {
            TypedQuery<LivroModel> query = entityManager.createQuery(
                    "SELECT l FROM LivroModel l WHERE l.titulo = :titulo", LivroModel.class);
            query.setParameter("titulo", livroNome);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum livro encontrado com esse nome.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
