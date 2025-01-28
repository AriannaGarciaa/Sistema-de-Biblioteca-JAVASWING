package Controller;

import Repository.UsuarioRepository;

public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void criarUsuario(String nome, String sexo, String celular, String email) {
        Usuario usuario = new Usuario(nome, sexo, celular, email);
        usuarioRepository.salvar(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public void atualizarUsuario(Long id, String nome, String sexo, String celular, String email) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
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

    public void deletarUsuario(Long id) {
        usuarioRepository.deletar(id);
        System.out.println("Usuário deletado com sucesso!");
    }
}
