package View;

import Controller.LivroController;
import Model.LivroModel;
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

public class CadastroLivro extends JFrame {
    private JPanel CadLivro;
    private JPanel cadLivro;
    private JTextField formattedTextFieldTitulo;
    private JTextField formattedTextFieldTema;
    private JTextField formattedTextFieldAutor;
    private JTextField formattedTextFieldISBN;
    private JFormattedTextField formattedTextFieldDataPublicacao;
    private JTextField formattedTextFieldQtdeDisponivel;
    private JButton cadastrarButton;
    private JButton cancelarButton;
    private LivroController livroController;

    public CadastroLivro(EntityManager em) {
        this.setTitle("Cadastro de Livro");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centraliza a janela

        this.livroController = new LivroController(em);

        cadLivro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        cadLivro.setBackground(Color.cyan);

        // Título
        cadLivro.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        formattedTextFieldTitulo = new JTextField(15);
        cadLivro.add(formattedTextFieldTitulo, gbc);

        // Tema
        gbc.gridx = 0;
        gbc.gridy++;
        cadLivro.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1;
        formattedTextFieldTema = new JTextField(15);
        cadLivro.add(formattedTextFieldTema, gbc);

        // Autor
        gbc.gridx = 0;
        gbc.gridy++;
        cadLivro.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        formattedTextFieldAutor = new JTextField(15);
        cadLivro.add(formattedTextFieldAutor, gbc);

        // ISBN
        gbc.gridx = 0;
        gbc.gridy++;
        cadLivro.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        formattedTextFieldISBN = new JTextField(15);
        cadLivro.add(formattedTextFieldISBN, gbc);

        // Data de Publicação
        gbc.gridx = 0;
        gbc.gridy++;
        cadLivro.add(new JLabel("Data de Publicação (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataPublicacao = createFormattedDateField();
        cadLivro.add(formattedTextFieldDataPublicacao, gbc);

        // Quantidade Disponível
        gbc.gridx = 0;
        gbc.gridy++;
        cadLivro.add(new JLabel("Quantidade Disponível:"), gbc);
        gbc.gridx = 1;
        formattedTextFieldQtdeDisponivel = new JTextField(15);
        cadLivro.add(formattedTextFieldQtdeDisponivel, gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy++;
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(Color.BLUE);
        cadastrarButton.setForeground(Color.WHITE);
        cadLivro.add(cadastrarButton, gbc);

        gbc.gridx = 1;
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(Color.RED);
        cancelarButton.setForeground(Color.WHITE);
        cadLivro.add(cancelarButton, gbc);

        this.add(cadLivro);

        this.setVisible(true);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarButton();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
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

    private void cadastrarButton() {
        String titulo = formattedTextFieldTitulo.getText().trim();
        String tema = formattedTextFieldTema.getText().trim();
        String autor = formattedTextFieldAutor.getText().trim();
        String isbn = formattedTextFieldISBN.getText().trim();

        if (titulo.isEmpty() || tema.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            return;
        }

        Date dataPublicacao = parseDate(formattedTextFieldDataPublicacao.getText());
        if (dataPublicacao == null) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Use o formato DD-MM-YYYY.");
            return;
        }

        int quantidadeDisponivel;
        try {
            quantidadeDisponivel = Integer.parseInt(formattedTextFieldQtdeDisponivel.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Quantidade disponível deve ser um número inteiro.");
            return;
        }

        LivroModel livro = new LivroModel();
        livro.setTitulo(titulo);
        livro.setTema(tema);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setDataPublicacao(dataPublicacao);
        livro.setQuantidadeDisponivel(quantidadeDisponivel);

        try {
            livroController.salvar(livro);
            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o livro: " + ex.getMessage());
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
        SwingUtilities.invokeLater(() -> new CadastroLivro(em));
    }
}
