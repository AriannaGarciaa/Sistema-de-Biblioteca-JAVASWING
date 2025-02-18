package View;

import Controller.EmprestimoController;
import Controller.LivroController;
import Controller.UsuarioController;
import Model.LivroModel;
import Repository.EmprestimoRepository;
import Repository.LivroRepository;
import Repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {
    private LivroController livroController;
    private LivroRepository livroRepo;
    private LivroModel livro;
    private JPanel jPanelPrincipal;
    private EmprestimoController emprestimoController;
    private EmprestimoRepository emprestimoRepo;
    private JMenuBar menuBar;
    private UsuarioRepository usuarioRepo;
    private UsuarioController usuarioController;
    private EntityManager em;

    public Principal() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        this.em = emf.createEntityManager();
        this.usuarioRepo = new UsuarioRepository(em);
        this.usuarioController = new UsuarioController(em);
        this.livroRepo = new LivroRepository(em);
        this.livroController = new LivroController(em);
        this.emprestimoRepo = new EmprestimoRepository(em);
        this.emprestimoController = new EmprestimoController(em);
        criacaoDoMenu();
        this.setTitle("Sistema de Gestão de Biblioteca");
        this.setContentPane(jPanelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public Principal(EntityManager entityManager) {
        this.em = entityManager;
        this.usuarioRepo = new UsuarioRepository(entityManager);
        this.usuarioController = new UsuarioController(entityManager);
        this.livroRepo = new LivroRepository(entityManager);
        this.livroController = new LivroController(entityManager);
        this.emprestimoRepo = new EmprestimoRepository(entityManager);
        this.emprestimoController = new EmprestimoController(entityManager);
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
        menuBar.setBackground(new Color(60, 63, 65));
        menuBar.setForeground(Color.WHITE);

        JMenu manterLivro = new JMenu("Manter Livro");
        manterLivro.setForeground(Color.WHITE);
        manterLivro.setIcon(redimensionarIcone("src/icons/livro.png", 20, 20));
        menuBar.add(manterLivro);
        JMenuItem cadastrarLivro = new JMenuItem("Cadastrar Livro");
        JMenuItem listarLivro = new JMenuItem("Listar Livro Disponivel");
        manterLivro.add(cadastrarLivro);
        manterLivro.add(listarLivro);

        JMenu emprestimo = new JMenu("Emprestimo de Livros");
        emprestimo.setForeground(Color.WHITE);
        emprestimo.setIcon(redimensionarIcone("src/icons/emprestimo.png", 20, 20));
        menuBar.add(emprestimo);
        JMenuItem emprestar = new JMenuItem("Emprestar Livro");
        JMenuItem devolver = new JMenuItem("Devolver Livro");
        JMenuItem listar = new JMenuItem("Listar Emprestimos Cadastrados");
        emprestimo.add(emprestar);
        emprestimo.add(devolver);
        emprestimo.add(listar);

        JMenu manterUsuario = new JMenu("Manter Usuario");
        manterUsuario.setForeground(Color.WHITE);
        manterUsuario.setIcon(redimensionarIcone("src/icons/usuario.png", 20, 20));
        menuBar.add(manterUsuario);
        JMenuItem cadastrarUsuario = new JMenuItem("Cadastrar Usuario");
        JMenuItem listarUsuario = new JMenuItem("Listar Usuario");
        manterUsuario.add(cadastrarUsuario);
        manterUsuario.add(listarUsuario);

        menuBar.add(emprestimo);
        menuBar.add(manterLivro);
        menuBar.add(manterUsuario);

        cadastrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroUsuario(em);
            }
        });

        listarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarUsuario(em);
            }
        });

        cadastrarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroLivro(em);
            }
        });
        listarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarLivro(em);
            }
        });

        emprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastrarEmprestimo(em);
            }
        });

        devolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DevolverEmprestimo(em);
            }
        });
        listar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListaEmprestimo(em);
            }
        });
    }
    private ImageIcon redimensionarIcone(String caminhoIcone, int largura, int altura) {
        ImageIcon icone = new ImageIcon(caminhoIcone);
        Image imagem = icone.getImage();
        Image imagemRedimensionada = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(imagemRedimensionada);
    }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal();
            }
        });
    }
}
