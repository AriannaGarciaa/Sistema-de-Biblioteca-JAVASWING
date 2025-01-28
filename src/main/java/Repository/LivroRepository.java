package Repository;
import Model.LivroModel;
import View.LivroView;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LivroRepository {
    public void salvar(LivroView livro) {
        Cache HibernateUtil = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(livro);
            transaction.commit();
        }
    }

    public List<LivroModel> listar() {
        Cache HibernateUtil = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Livro", LivroModel.class).list();
        }
    }

    public void remover(int id) {
        Cache HibernateUtil = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            LivroModel livro = session.get(LivroModel.class, id);
            if (livro != null) session.delete(livro);
            transaction.commit();
        }
    }
}
