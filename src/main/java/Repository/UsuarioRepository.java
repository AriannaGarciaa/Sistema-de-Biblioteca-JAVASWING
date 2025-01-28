package Repository;

import Model.UsuarioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UsuarioRepository {

    private static UsuarioRepository instance;
    private EntityManager entityManager;

    public UsuarioRepository() {
        entityManager = JpaUtil.getEntityManager();
    }

    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }

    // Criar um usuário (CREATE)
    public void salvar(UsuarioModel usuario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(usuario);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    // Buscar um usuário por ID (READ)
    public UsuarioModel buscarPorId(int id) {
        return entityManager.find(UsuarioModel.class, id);
    }

    // Listar todos os usuários (READ)
    public List<UsuarioModel> listarTodos() {
        TypedQuery<UsuarioModel> query = entityManager.createQuery("SELECT u FROM UsuarioModel u", UsuarioModel.class);
        return query.getResultList();
    }

    // Atualizar um usuário (UPDATE)
    public void atualizar(UsuarioModel usuario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(usuario); // Certifique-se de que usuario está sendo reconhecido
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    // Deletar um usuário por ID (DELETE)
    public void deletar(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            UsuarioModel usuario = entityManager.find(UsuarioModel.class, id);
            if (usuario != null) {
                entityManager.remove(usuario);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
