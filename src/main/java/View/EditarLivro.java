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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class EditarLivro extends JFrame {
    private JTextField textFieldTitulo;
    private JTextField textFieldTema;
    private JTextField textFieldAutor;
    private JTextField textFieldISBN;
    private JFormattedTextField formattedTextFieldDataPublicacao;
    private JTextField textFieldQuantidadeDisponivel;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JPanel panel;
    private LivroController livroController;
    private LivroModel livro;

    public EditarLivro(EntityManager em, LivroModel livro) {
        this.livroController = new LivroController(em);
        this.livro = livro;
        this.setTitle("Editar Livro");
        this.setSize(450, 350);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.setBackground(Color.cyan);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        textFieldTitulo = new JTextField(15);
        textFieldTitulo.setText(livro.getTitulo());
        panel.add(textFieldTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 1;
        textFieldTema = new JTextField(15);
        textFieldTema.setText(livro.getTema());
        panel.add(textFieldTema, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        textFieldAutor = new JTextField(15);
        textFieldAutor.setText(livro.getAutor());
        panel.add(textFieldAutor, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        textFieldISBN = new JTextField(15);
        textFieldISBN.setText(livro.getIsbn());
        panel.add(textFieldISBN, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Data de Publicação (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataPublicacao = createFormattedDateField();
        formattedTextFieldDataPublicacao.setText(formatDate(livro.getDataPublicacao()));
        panel.add(formattedTextFieldDataPublicacao, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Quantidade Disponível:"), gbc);
        gbc.gridx = 1;
        textFieldQuantidadeDisponivel = new JTextField(15);
        textFieldQuantidadeDisponivel.setText(String.valueOf(livro.getQuantidadeDisponivel()));
        panel.add(textFieldQuantidadeDisponivel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        salvarButton = new JButton("Salvar");
        salvarButton.setBackground(Color.BLUE);
        salvarButton.setForeground(Color.WHITE);
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(Color.RED);
        cancelarButton.setForeground(Color.WHITE);
        panel.add(salvarButton, gbc);
        gbc.gridx = 1;
        panel.add(cancelarButton, gbc);

        salvarButton.addActionListener(this::salvarAlteracoes);
        cancelarButton.addActionListener(e -> dispose());

        this.add(panel);
        this.setVisible(true);
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

    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    private void salvarAlteracoes(ActionEvent e) {
        livro.setTitulo(textFieldTitulo.getText().trim());
        livro.setTema(textFieldTema.getText().trim());
        livro.setAutor(textFieldAutor.getText().trim());
        livro.setIsbn(textFieldISBN.getText().trim());
        livro.setDataPublicacao(parseDate(formattedTextFieldDataPublicacao.getText()));
        livro.setQuantidadeDisponivel(Integer.parseInt(textFieldQuantidadeDisponivel.getText().trim()));

        try {
            livroController.atualizarLivro(livro);
            JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar livro: " + ex.getMessage());
        }
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            return new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        LivroModel livro = em.find(LivroModel.class, 1); // Carregue o livro com ID 1, por exemplo
        SwingUtilities.invokeLater(() -> new EditarLivro(em, livro));
    }
}
