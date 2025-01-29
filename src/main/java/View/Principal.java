package View;

import Repository.LivroRepository;
import Repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {
    private JPanel jPanelPrincipal;
    private JMenuBar menuBar;
    private LivroRepository livroRepo;
    private UsuarioRepository usuarioRepo;

    public Principal() {
        this.livroRepo = livroRepo;
        this.usuarioRepo = usuarioRepo;
        criacaoDoMenu();
        this.setTitle("Sistema de Gestão de Biblioteca");
        this.setContentPane(jPanelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void criacaoDoMenu() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu arquivo = new JMenu("Arquivo");
        JMenuItem opcao1 = new JMenuItem("Opção 1");
        JMenuItem opcao2 = new JMenuItem("Opção 2");
        arquivo.add(opcao1);
        arquivo.add(opcao2);

        JMenu manterLivro = new JMenu("Manter Livro");
        JMenuItem cadastrarLivro = new JMenuItem("Cadastrar Livro");
        JMenuItem editarLivro = new JMenuItem("Editar Livro");
        JMenuItem listarLivro = new JMenuItem("Listar Livro");
        JMenuItem excluirLivro = new JMenuItem("Excluir Livro");
        manterLivro.add(cadastrarLivro);
        manterLivro.add(editarLivro);
        manterLivro.add(listarLivro);
        manterLivro.add(excluirLivro);

        JMenu manterUsuario = new JMenu("Manter Usuario");
        JMenuItem cadastrarUsuario = new JMenuItem("Cadastrar Usuario");
        JMenuItem editarUsuario = new JMenuItem("Editar Usuario");
        JMenuItem listarUsuario = new JMenuItem("listar Usuario");
        JMenuItem excluirUsuario = new JMenuItem("Excluir Usuario");
        manterUsuario.add(cadastrarUsuario);
        manterUsuario.add(editarUsuario);
        manterUsuario.add(listarUsuario);
        manterUsuario.add(excluirUsuario);

        menuBar.add(arquivo);
        menuBar.add(manterLivro);
        menuBar.add(manterUsuario);
        cadastrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Usuario();
            }
        });
        editarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        listarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        excluirUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence.xml");
                EntityManager em = emf.createEntityManager();
                new Principal();
            }
        });
    }
}
