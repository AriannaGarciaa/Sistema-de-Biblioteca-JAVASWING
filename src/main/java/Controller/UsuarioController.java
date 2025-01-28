package Controller;

import Model.UsuarioModel;
import Repository.UsuarioRepository;
import java.util.List;

public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void criarUsuario(String nome, String sexo, String celular, String email) {
        UsuarioModel usuario = new UsuarioModel( nome, sexo, celular, email);
        usuarioRepository.salvar(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public UsuarioModel buscarUsuarioPorId(int id) {
        UsuarioModel usuario = usuarioRepository.buscarPorId(id);
        if (usuario != null) {
            return usuario;
        } else {
            System.out.println("Usuário não encontrado!");
            return null;
        }
    }

    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public void atualizarUsuario(int id, String nome, String sexo, String celular, String email) {
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
    }

    public void deletarUsuario(int id) {
        UsuarioModel usuario = usuarioRepository.buscarPorId(id);
        if (usuario != null) {
            usuarioRepository.deletar(id);
            System.out.println("Usuário deletado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }
}
