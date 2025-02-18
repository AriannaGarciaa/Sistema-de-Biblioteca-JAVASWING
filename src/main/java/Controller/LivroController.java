package Controller;

import Model.LivroModel;
import Repository.LivroRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.SQLException;
import java.util.List;

public class LivroController {
    private final LivroRepository livroRepository;
    private final EntityManager entityManager;

    public LivroController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.livroRepository = new LivroRepository(entityManager);
    }

    public String salvar(LivroModel livro) throws SQLException {
        return livroRepository.salvar(livro);
    }

    public LivroModel buscarPorId(int id) {
        try {
            return livroRepository.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
            return null;
        }
    }

    public void atualizarLivro(LivroModel livro) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(livro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    public void atualizarQuantidadeDisponivel(int id, int novaQuantidade) {
        try {
            LivroModel livro = livroRepository.buscarPorId(id);
            if (livro != null) {
                if (novaQuantidade >= 0) {
                    livro.setQuantidadeDisponivel(novaQuantidade);
                    atualizarLivro(livro);
                    System.out.println("Quantidade disponível do livro atualizada com sucesso!");
                } else {
                    System.out.println("Erro: A quantidade disponível não pode ser negativa!");
                }
            } else {
                System.out.println("Erro: Livro não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar quantidade disponível: " + e.getMessage());
        }
    }

    public void deletarLivro(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            LivroModel livro = livroRepository.buscarPorId(id);
            if (livro != null) {
                transaction.begin();
                entityManager.remove(livro);
                transaction.commit();
                System.out.println("Livro deletado com sucesso!");
            } else {
                System.out.println("Livro não encontrado!");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao deletar livro: " + e.getMessage());
        }
    }

    public LivroModel buscarPorNome(String livroNome) {
        try {
            return livroRepository.buscarPorNome(livroNome);
        } catch (Exception e) {
            System.out.println("Erro ao buscar livro pelo nome: " + e.getMessage());
            return null;
        }
    }

    public List<LivroModel> listarTodos() {
        try {
            return livroRepository.listarTodos();
        } catch (Exception e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
            return null;
        }
    }
}
