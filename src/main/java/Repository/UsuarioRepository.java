package Repository;

import javax.swing.text.html.parser.Entity;

public class UsuarioRepository {

    private static UsuarioRepository instance;
    protected EntityManager = getEntityManager();

    public UsuarioRepository getEntityManager() {
        entityManager = getEntityManager();
    }

    private EntityManager(){
        EntityManagerFactory factory  = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }

}

