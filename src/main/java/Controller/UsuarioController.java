package Controller;

import Model.UsuarioModel;
import Repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
<<<<<<< HEAD
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
=======
import jakarta.persistence.TypedQuery;

>>>>>>> origin/main
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final EntityManager entityManager;

    public UsuarioController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.usuarioRepository = new UsuarioRepository(entityManager);
    }

<<<<<<< HEAD
    public String salvar(UsuarioModel usuario) throws SQLException {
        return usuarioRepository.salvar(usuario);
    }

=======
    public void criarUsuario(String nome, String sexo, String celular, String email) {
        try {
            UsuarioModel usuario = new UsuarioModel(nome, sexo, celular, email);
            usuarioRepository.salvar(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public String salvar(UsuarioModel usuario) throws SQLException {
        return usuarioRepository.salvar(usuario);
    }

>>>>>>> origin/main
    public UsuarioModel buscarUsuarioPorId(int id) {
        try {
            return usuarioRepository.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            return null;
        }
    }

    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public void atualizarUsuario(int id, String nome, String sexo, String celular, String email) {
        try {
            UsuarioModel usuario = usuarioRepository.buscarPorId(id);
            if (usuario != null) {
                usuario.setNome(nome);
                usuario.setSexo(sexo);
                usuario.setCelular(celular);
                usuario.setEmail(email);
                usuarioRepository.atualizar(usuario);
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

<<<<<<< HEAD
    public boolean deletarUsuario(int id) {
=======
    public Object deletarUsuario(int id) {
>>>>>>> origin/main
        try {
            UsuarioModel usuario = usuarioRepository.buscarPorId(id);
            if (usuario != null) {
                usuarioRepository.deletar(id);
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
        }
<<<<<<< HEAD
        return false;
    }

    public int contarLivrosEmprestados(int usuarioId) {
        try {
            String jpql = "SELECT COUNT(e) FROM EmprestimoModel e WHERE e.usuario.id = :usuarioId AND e.devolvido = false";
            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("usuarioId", usuarioId);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            System.out.println("Erro ao contar livros emprestados: " + e.getMessage());
            return 0;
        }
    }

    public String remover(Long idUsuarioSelecionado) throws SQLException {
        UsuarioModel usuario = usuarioRepository.buscarPorId(Math.toIntExact(idUsuarioSelecionado));
        return usuarioRepository.remover(usuario);
    }

    public UsuarioModel buscarPorId(int id) {
        try {
            return usuarioRepository.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            return null;
        }
    }

    public UsuarioModel buscarPorNome(String usuarioNome) {
        try {
            TypedQuery<UsuarioModel> query = entityManager.createQuery(
                    "SELECT u FROM UsuarioModel u WHERE LOWER(u.nome) = LOWER(:nome)", UsuarioModel.class);
            query.setParameter("nome", usuarioNome);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Usuário não encontrado!");
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário por nome: " + e.getMessage());
            return null;
        }
=======
        return null;
>>>>>>> origin/main
    }
    // Método para contar quantos livros o usuário pegou emprestado
    public int contarLivrosEmprestados(int usuarioId) {
        // Consulta JPQL para contar o número de empréstimos do usuário
        String jpql = "SELECT COUNT(e) FROM EmprestimoModel e WHERE e.usuario.id = :usuarioId AND e.devolvido = false";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("usuarioId", usuarioId);

        Long count = query.getSingleResult();
        return count != null ? count.intValue() : 0;
    }
    public String remover (Long idUsuarioSelecionado) throws SQLException {
        UsuarioModel usuario = usuarioRepository.buscarPorId(Math.toIntExact(idUsuarioSelecionado));
        return usuarioRepository.remover(usuario);
    }

}
