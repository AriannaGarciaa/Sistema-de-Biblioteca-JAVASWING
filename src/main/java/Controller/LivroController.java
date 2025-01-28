package Controller;

import Repository.LivroRepository;
import View.LivroView;

import java.util.List;

public class LivroController {
    public LivroRepository livroRepository;

    public LivroController() {
        this.livroRepository = new LivroRepository();
    }

    public void salvarLivro(LivroView livro) {
        livroRepository.salvar(livro);
    }

    public List<Livro> listarLivros() {
        return livroRepository.listar();
    }

    public void removerLivro(Long id) {
        livroRepository.remover(id);
    }
}
