package View;

import Controller.LivroController;
import Model.LivroModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LivroView extends JFrame {
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

        JButton cadastrarButton = new JButton("Salvar");
        cadastrarButton.addActionListener(e -> salvarLivro());
        add(cadastrarButton);
    }


    private void salvarLivro() {
        LivroModel livro = new LivroModel();
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
