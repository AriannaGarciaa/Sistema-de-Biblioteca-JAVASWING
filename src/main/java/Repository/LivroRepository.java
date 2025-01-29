package Repository;

import Model.LivroModel;
import jakarta.persistence.*;

import java.util.List;

public class LivroRepository {
    private static LivroRepository instance;
    protected static EntityManager entityManager;

    // Construtor
    public LivroRepository() {
        LivroRepository.entityManager = getEntityManager();
    }

    // Método para criar e obter o EntityManager
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    // Método Singleton para obter uma instância
    public static LivroRepository getInstance() {
        if (instance == null) {
            instance = new LivroRepository();  // Inicializa a instância
        }
        return instance;
    }

    // Criar um livro (CREATE)
    public void salvar(LivroModel livro) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(livro);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    // Buscar um livro por ID (READ)
    public LivroModel buscarPorId(int id) {
        return entityManager.find(LivroModel.class, id);
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

    public void remover(int id) {
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

    public List<LivroModel> listar() {
        TypedQuery<LivroModel> query = entityManager.createQuery("SELECT l FROM LivroModel l", LivroModel.class);
        return query.getResultList();
    }
}
