package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LivroView extends Jframe{
    private JTextField tituloField;
    private JTextField temaField;
    private JTextField autorField;
    private JTextField isbnField;
    private JTextField quantidadeField;

    private LivroController livroController;

    public LivroView() {
        this.livroController = new LivroController();

        setTitle("Gestão de Livros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Título:"));
        tituloField = new JTextField();
        add(tituloField);

        add(new JLabel("Tema:"));
        temaField = new JTextField();
        add(temaField);

        add(new JLabel("Autor:"));
        autorField = new JTextField();
        add(autorField);

        add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        add(isbnField);

        add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        add(quantidadeField);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarLivro());
        add(salvarButton);
    }

    private void salvarLivro() {
        Livro livro = new Livro();
        livro.setTitulo(tituloField.getText());
        livro.setTema(temaField.getText());
        livro.setAutor(autorField.getText());
        livro.setIsbn(isbnField.getText());
        livro.setQuantidadeDisponivel(Integer.parseInt(quantidadeField.getText()));

        livroController.salvarLivro(livro);
        JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
    }

    public static void main(String[] args) {
        new LivroView().setVisible(true);
    }
}
