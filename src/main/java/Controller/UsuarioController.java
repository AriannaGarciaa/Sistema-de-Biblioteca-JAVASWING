package Controller;

import Model.UsuarioModel;
import Repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.Collections;
import java.util.List;


public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final EntityManager entityManager;

    // Construtor com tratamento de exceções para garantir a criação do repositório
    // Construtor atualizado
    public UsuarioController() {
        // Criação do EntityManager
        this.entityManager = Persistence.createEntityManagerFactory("suaPU").createEntityManager();
        // Passando o EntityManager para o Repository
        this.usuarioRepository = new UsuarioRepository(entityManager);
    }

    // Método para criar um novo usuário com tratamento de exceções
    public void criarUsuario(String nome, String sexo, String celular, String email) {
        try {
            UsuarioModel usuario = new UsuarioModel(nome, sexo, celular, email);
            usuarioRepository.salvar(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    // Método para buscar um usuário por ID, com verificação e mensagem caso não seja encontrado
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

    // Método para listar todos os usuários com verificação de lista vazia
    public List<UsuarioModel> listarUsuarios() {
        try {
            List<UsuarioModel> usuarios = usuarioRepository.listarTodos();
            if (usuarios == null || usuarios.isEmpty()) {
                System.out.println("Nenhum usuário encontrado.");
                return Collections.emptyList(); // Retorna uma lista vazia
            }
            return usuarios;
        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
            return Collections.emptyList(); // Retorna uma lista vazia em caso de erro
        }
    }

    // Método para atualizar um usuário, com verificação de existência e tratamento de exceções
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

    // Método para deletar um usuário, com verificação de existência e tratamento de exceções
    public void deletarUsuario(int id) {
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
    }
}
