package Controller;

import Model.UsuarioModel;
import Repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final EntityManager entityManager;

    public UsuarioController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.usuarioRepository = new UsuarioRepository(entityManager);
    }

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

    public UsuarioModel buscarUsuarioPorId(int id) {
        try {
            UsuarioModel usuario = usuarioRepository.buscarPorId(id);
            if (usuario != null) {
                return usuario;
            } else {
                System.out.println("Usuário não encontrado!");
                return null;
            }
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

    public Object deletarUsuario(int id) {
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
        return null;
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
