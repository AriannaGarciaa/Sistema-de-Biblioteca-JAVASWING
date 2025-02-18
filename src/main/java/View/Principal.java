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

<<<<<<< HEAD
import java.awt.*;
=======
>>>>>>> origin/main
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Principal extends JFrame {
    private LivroController livroController;
    private LivroRepository livroRepo;
<<<<<<< HEAD
    private LivroModel livro;
=======
>>>>>>> origin/main
    private JPanel jPanelPrincipal;
    private EmprestimoController emprestimoController;
    private EmprestimoRepository emprestimoRepo;
    private JMenuBar menuBar;
    private UsuarioRepository usuarioRepo;
    private UsuarioController usuarioController;
    private EntityManager em;

    // Construtor Sem Argumentos
    public Principal() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        this.em = emf.createEntityManager();
        this.usuarioRepo = new UsuarioRepository(em);
        this.usuarioController = new UsuarioController(em);
        this.livroRepo = new LivroRepository(em);
        this.livroController = new LivroController(em);
        this.emprestimoRepo = new EmprestimoRepository(em);
        this.emprestimoController = new EmprestimoController(em);
<<<<<<< HEAD
=======
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
>>>>>>> origin/main
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
<<<<<<< HEAD
        menuBar.setBackground(new Color(60, 63, 65));
        menuBar.setForeground(Color.WHITE);
=======
>>>>>>> origin/main

        JMenu manterLivro = new JMenu("Manter Livro");
        manterLivro.setForeground(Color.WHITE);
        manterLivro.setIcon(redimensionarIcone("src/icons/livro.png", 20, 20));
        menuBar.add(manterLivro);
        JMenuItem cadastrarLivro = new JMenuItem("Cadastrar Livro");
<<<<<<< HEAD
        JMenuItem listarLivro = new JMenuItem("Listar Livro Disponivel");
=======
        JMenuItem editarLivro = new JMenuItem("Editar Livro");
        JMenuItem listarLivro = new JMenuItem("Listar Livro");
        JMenuItem listarLivroDisponivel = new JMenuItem("Listar Livro Disponivel");
>>>>>>> origin/main
        manterLivro.add(cadastrarLivro);
        manterLivro.add(listarLivro);
<<<<<<< HEAD

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
=======
        manterLivro.add(listarLivroDisponivel);

        JMenu emprestimo = new JMenu("Emprestimo de Livros");
        JMenuItem emprestar = new JMenuItem("Emprestar Livro");
        JMenuItem devolver = new JMenuItem("Devolver Livro");
        emprestimo.add(emprestar);
        emprestimo.add(devolver);
>>>>>>> origin/main

        JMenu manterUsuario = new JMenu("Manter Usuario");
        manterUsuario.setForeground(Color.WHITE);
        manterUsuario.setIcon(redimensionarIcone("src/icons/usuario.png", 20, 20));
        menuBar.add(manterUsuario);
        JMenuItem cadastrarUsuario = new JMenuItem("Cadastrar Usuario");
<<<<<<< HEAD
=======
        JMenuItem editarUsuario = new JMenuItem("Editar Usuario");
>>>>>>> origin/main
        JMenuItem listarUsuario = new JMenuItem("Listar Usuario");
        manterUsuario.add(cadastrarUsuario);
        manterUsuario.add(listarUsuario);

        menuBar.add(emprestimo);
        menuBar.add(manterLivro);
        menuBar.add(manterUsuario);

        cadastrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
                new CadastroUsuario(em);
=======
                new CadastroUsuario(em); // Passa o EntityManager ao criar a instância da classe Usuario
            }
        });

        editarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
>>>>>>> origin/main
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
<<<<<<< HEAD
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
=======
                new CadastroLivro(em); // Passa o EntityManager ao criar a instância da classe Usuario
>>>>>>> origin/main
            }
        });
        editarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLivro();
            }
        });
        listarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarLivro(em);
            }
        });
        listarLivroDisponivel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<LivroModel> livrosDisponiveis = livroController.buscarLivrosDisponiveis();

                if (livrosDisponiveis.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum livro disponível no momento.");
                } else {
                    // Ordenar a lista de livros por título
                    livrosDisponiveis.sort(Comparator.comparing(LivroModel::getTitulo));

                    // Se quiser ordenar pela quantidade disponível, use:
                    // livrosDisponiveis.sort(Comparator.comparingInt(LivroModel::getQuantidadeDisponivel));

                    StringBuilder mensagem = new StringBuilder("Livros Disponíveis:\n\n");
                    mensagem.append(String.format("%-5s %-30s %-20s\n", "ID", "Título", "Quantidade Disponível"));
                    mensagem.append("---------------------------------------------------------\n");

                    for (LivroModel livro : livrosDisponiveis) {
                        mensagem.append(String.format("%-5d %-30s %-20d\n",
                                livro.getId(),
                                livro.getTitulo(),
                                livro.getQuantidadeDisponivel()));
                    }

                    JOptionPane.showMessageDialog(null, mensagem.toString());
                }
            }
        });



        emprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastrarEmprestimo(em); // Passa o EntityManager ao criar a instância da classe Emprestimo
            }
        });

        devolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DevolverEmprestimo(em);
            }
        });

    }
<<<<<<< HEAD
    private ImageIcon redimensionarIcone(String caminhoIcone, int largura, int altura) {
        ImageIcon icone = new ImageIcon(caminhoIcone);
        Image imagem = icone.getImage();
        Image imagemRedimensionada = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(imagemRedimensionada);
    }
=======

    // Método para editar usuário
    private void editarUsuario() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do Usuário:"));
            String nome = JOptionPane.showInputDialog(" Atualize Nome:");
            String sexo = JOptionPane.showInputDialog("Atualize o Sexo:");
            String celular = JOptionPane.showInputDialog("Atualize o Celular:");
            String email = JOptionPane.showInputDialog("Atualize o  Email:");
            usuarioController.atualizarUsuario(id, nome, sexo, celular, email);
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário: " + e.getMessage());
        }
    }


    private void editarLivro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do Livro:"));
            String titulo = JOptionPane.showInputDialog(" Atualize Ttitulo:");
            String tema = JOptionPane.showInputDialog("Atualize o Tema:");
            String autor = JOptionPane.showInputDialog("Atualize o autor:");
            String isbn = JOptionPane.showInputDialog("Atualize o  ISBN:");
            String dataPublicacaoString = JOptionPane.showInputDialog("Atualize Data de Publicação (YYYY-MM-DD):");
            Integer quantidadeDisponivel = Integer.valueOf(JOptionPane.showInputDialog("Atualize a quantidade Disponivel:"));

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
            Date dataPublicacao = sdf.parse(dataPublicacaoString);  // Aqui você converte a string para Date

            livroController.atualizarLivro(id, titulo, tema, autor, isbn, dataPublicacao, quantidadeDisponivel);
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar livro: " + e.getMessage());
        }
    }


>>>>>>> origin/main
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
<<<<<<< HEAD
                new Principal();
=======
                new Principal(); // Chama o construtor sem argumentos
>>>>>>> origin/main
            }
        });
    }
}
