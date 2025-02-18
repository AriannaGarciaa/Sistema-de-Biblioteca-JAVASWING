package View;

import Controller.EmprestimoController;
import Controller.LivroController;
import Controller.UsuarioController;
import Model.EmprestimoModel;
import Model.LivroModel;
import Model.UsuarioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CadastrarEmprestimo extends JFrame {
    private JTextField textFieldUsuarioNome;
    private JTextField textFieldLivroTitulo;
    private JFormattedTextField formattedTextFieldDataEmprestimo;
    private JFormattedTextField formattedTextFieldDataDevolucao;
    private JCheckBox checkBoxDevolvido;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JPanel panel1;
    private EmprestimoController emprestimoController;
    private UsuarioController usuarioController;
    private LivroController livroController;

    public CadastrarEmprestimo(EntityManager em) {
        this.setTitle("Cadastro de Empréstimo");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centraliza a janela

        this.emprestimoController = new EmprestimoController(em);
        this.usuarioController = new UsuarioController(em);
        this.livroController = new LivroController(em);

        panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.setBackground(Color.cyan);

        panel1.add(new JLabel("Nome do Usuário:"), gbc);
        gbc.gridx = 1;
        textFieldUsuarioNome = new JTextField(15);
        panel1.add(textFieldUsuarioNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel1.add(new JLabel("Título do Livro:"), gbc);
        gbc.gridx = 1;
        textFieldLivroTitulo = new JTextField(15);
        panel1.add(textFieldLivroTitulo, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panel1.add(new JLabel("Data de Empréstimo (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataEmprestimo = createFormattedDateField();
        panel1.add(formattedTextFieldDataEmprestimo, gbc);

        formattedTextFieldDataEmprestimo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                atualizarDataDevolucao();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                atualizarDataDevolucao();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                atualizarDataDevolucao();
            }
        });


        gbc.gridx = 0;
        gbc.gridy++;
        panel1.add(new JLabel("Data de Devolução Prevista (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataDevolucao = createFormattedDateField();
        panel1.add(formattedTextFieldDataDevolucao, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel1.add(salvarButton, gbc);
        gbc.gridx = 1;
        panel1.add(cancelarButton, gbc);
        salvarButton.setForeground(Color.BLACK);
        cancelarButton.setForeground(Color.BLACK);

        this.add(panel1);


        JMenuBar menuBar = new JMenuBar();

        JMenu menuUsuarios = new JMenu("Usuários");
        menuBar.add(menuUsuarios);

        JMenu menuLivros = new JMenu("Livros");
        menuBar.add(menuLivros);

        carregarUsuariosNoMenu(menuUsuarios);
        carregarLivrosNoMenu(menuLivros);

        this.setJMenuBar(menuBar);

        this.setVisible(true);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEmprestimo();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private void atualizarDataDevolucao() {
        String dataEmprestimoStr = formattedTextFieldDataEmprestimo.getText().trim();
        Date dataEmprestimo = parseDate(dataEmprestimoStr);

        if (dataEmprestimo != null) {
            // Adiciona 14 dias
            long milissegundosPorDia = 24L * 60 * 60 * 1000;
            Date dataDevolucao = new Date(dataEmprestimo.getTime() + (14 * milissegundosPorDia));

            // Formata a data no formato desejado
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            formattedTextFieldDataDevolucao.setText(sdf.format(dataDevolucao));
        }
    }

    private JFormattedTextField createFormattedDateField() {
        try {
            MaskFormatter dateMask = new MaskFormatter("##-##-####");
            dateMask.setPlaceholderCharacter('_');
            return new JFormattedTextField(dateMask);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao configurar máscara de data: " + ex.getMessage());
            return new JFormattedTextField();
        }
    }

    private void carregarUsuariosNoMenu(JMenu menuUsuarios) {
        List<UsuarioModel> usuarios = usuarioController.listarTodos();
        for (UsuarioModel usuario : usuarios) {
            JMenuItem usuarioItem = new JMenuItem(usuario.getNome());
            usuarioItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textFieldUsuarioNome.setText(usuario.getNome());
                }
            });
            menuUsuarios.add(usuarioItem);
        }
    }

    private void carregarLivrosNoMenu(JMenu menuLivros) {
        List<LivroModel> livros = livroController.listarTodos();
        for (LivroModel livro : livros) {
            JMenuItem livroItem = new JMenuItem(livro.getTitulo());
            livroItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textFieldLivroTitulo.setText(livro.getTitulo());
                }
            });
            menuLivros.add(livroItem);
        }
    }

    private void salvarEmprestimo() {
        String usuarioNome = textFieldUsuarioNome.getText().trim();
        String livroNome = textFieldLivroTitulo.getText().trim();

        if (usuarioNome.isEmpty() || livroNome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            return;
        }

        UsuarioModel usuario = usuarioController.buscarPorNome(usuarioNome);
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
            return;
        }

        // Verifica se o usuário já tem 5 livros emprestados
        int livrosEmprestados = usuarioController.contarLivrosEmprestados(usuario.getId());
        if (livrosEmprestados >= 5) {
            JOptionPane.showMessageDialog(null, "O usuário já atingiu o limite de 5 livros emprestados.");
            return;
        }

        LivroModel livro = livroController.buscarPorNome(livroNome);
        if (livro == null || livro.getQuantidadeDisponivel() <= 0) {
            JOptionPane.showMessageDialog(null, "Livro não encontrado ou sem exemplares disponíveis.");
            return;
        }

        Date dataEmprestimo = parseDate(formattedTextFieldDataEmprestimo.getText());
        Date dataDevolucao = parseDate(formattedTextFieldDataDevolucao.getText());

        if (dataEmprestimo == null || dataDevolucao == null) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Use o formato DD-MM-YYYY.");
            return;
        }

        if (dataDevolucao.before(dataEmprestimo)) {
            JOptionPane.showMessageDialog(null, "A data de devolução deve ser posterior à data de empréstimo.");
            return;
        }

        boolean devolvido = checkBoxDevolvido.isSelected();

        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);
        emprestimo.setDevolvido(devolvido);

        try {
            emprestimoController.salvar(emprestimo);
            livroController.atualizarQuantidadeDisponivel(livro.getId(), livro.getQuantidadeDisponivel() - 1);
            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar empréstimo: " + ex.getMessage());
        }
    }


    private Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            if (!dateStr.isEmpty() && !dateStr.contains("_")) {
                return new Date(sdf.parse(dateStr).getTime());
            }
        } catch (ParseException ex) {
            return null;
        }
        return null;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        SwingUtilities.invokeLater(() -> new CadastrarEmprestimo(em));
    }
}
