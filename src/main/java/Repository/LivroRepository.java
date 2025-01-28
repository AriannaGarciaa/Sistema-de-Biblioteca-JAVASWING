package Repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LivroRepository {
    public void salvar(Livro livro) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(livro);
            transaction.commit();
        }
    }

    public List<Livro> listar() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Livro", Livro.class).list();
        }
    }

    public void remover(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Livro livro = session.get(Livro.class, id);
            if (livro != null) session.delete(livro);
            transaction.commit();
        }
    }
}
