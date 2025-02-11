package Controller;

import Model.LivroModel;
import Repository.LivroRepository;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
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
            LivroModel livro = livroRepository.buscarPorId(id);
            if (livro != null) {
                return livro;
            } else {
                System.out.println("Livro não encontrado!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
            return null;
        }
    }

    public void atualizarLivro(int id, String titulo, String tema, String autor, String isbn, Date dataPublicacao, int quantidadeDisponivel) {
        try {
            LivroModel livro = livroRepository.buscarPorId(id);
            if (livro != null) {
                livro.setTitulo(titulo);
                livro.setTema(tema);
                livro.setAutor(autor);
                livro.setIsbn(isbn);
                livro.setDataPublicacao(dataPublicacao);
                livro.setQuantidadeDisponivel(quantidadeDisponivel);
                livroRepository.atualizar(livro);
                System.out.println("Livro atualizado com sucesso!");
            } else {
                System.out.println("Livro não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    // Método para atualizar a quantidade de exemplares disponíveis de um livro
    public void atualizarQuantidadeDisponivel(int id, int novaQuantidade) {
        try {
            LivroModel livro = livroRepository.buscarPorId(id);
            if (livro != null) {
                if (novaQuantidade >= 0) {
                    livro.setQuantidadeDisponivel(novaQuantidade);
                    livroRepository.atualizar(livro);  // Atualiza o livro no banco de dados
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
    public Object deletarLivro(int id) {
        try {
            LivroModel livro = livroRepository.buscarPorId(id);
            if (livro != null) {
                livroRepository.deletar(id);
                System.out.println("Livro deletado com sucesso!");
            } else {
                System.out.println("Livro não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar Livro: " + e.getMessage());
        }
        return null;
    }
    // Método para buscar livros disponíveis
    public List<LivroModel> buscarLivrosDisponiveis() {
        return livroRepository.buscarLivrosDisponiveis();
    }


}