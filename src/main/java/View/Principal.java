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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Principal extends JFrame {
    private LivroController livroController;
    private LivroRepository livroRepo;
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

        JMenu manterLivro = new JMenu("Manter Livro");
        JMenuItem cadastrarLivro = new JMenuItem("Cadastrar Livro");
        JMenuItem editarLivro = new JMenuItem("Editar Livro");
        JMenuItem listarLivro = new JMenuItem("Listar Livro");
        JMenuItem listarLivroDisponivel = new JMenuItem("Listar Livro Disponivel");
        manterLivro.add(cadastrarLivro);
        manterLivro.add(editarLivro);
        manterLivro.add(listarLivro);
        manterLivro.add(listarLivroDisponivel);

        JMenu emprestimo = new JMenu("Emprestimo de Livros");
        JMenuItem emprestar = new JMenuItem("Emprestar Livro");
        JMenuItem devolver = new JMenuItem("Devolver Livro");
        JMenuItem listar = new JMenuItem("Listar Emprestimos Cadastrados");
        emprestimo.add(emprestar);
        emprestimo.add(devolver);
        emprestimo.add(listar);

        JMenu manterUsuario = new JMenu("Manter Usuario");
        JMenuItem cadastrarUsuario = new JMenuItem("Cadastrar Usuario");
        JMenuItem editarUsuario = new JMenuItem("Editar Usuario");
        JMenuItem listarUsuario = new JMenuItem("Listar Usuario");
        manterUsuario.add(cadastrarUsuario);
        manterUsuario.add(editarUsuario);
        manterUsuario.add(listarUsuario);

        menuBar.add(emprestimo);
        menuBar.add(manterLivro);
        menuBar.add(manterUsuario);

        cadastrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroUsuario(em); // Passa o EntityManager ao criar a instância da classe Usuario
            }
        });

        editarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
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
                new CadastroLivro(em); // Passa o EntityManager ao criar a instância da classe Usuario
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
        listar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListaEmprestimo(em);
            }
        }
        );

    }

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


        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal();
            }
        });
    }
}
