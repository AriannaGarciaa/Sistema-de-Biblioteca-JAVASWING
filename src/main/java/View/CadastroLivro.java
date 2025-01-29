package View;

import javax.swing.*;

import Controller.LivroController;

import java.awt.event.*;

public class CadastroLivro extends JFrame {
    private JPanel cadLivro;
    private JPanel CadLivro;
    private JTextField formattedTextFieldTitulo;
    private JTextField formattedTextFieldTema;
    private JTextField formattedTextFieldAutor;
    private JTextField formattedTextFieldISBN;
    private JTextField formattedTextFieldDataPublicacao;
    private JTextField formattedTextFieldQtdeDisponivel;
    private JButton cadastrarButton;
    private LivroController controller;

    public CadastroLivro() {
        this.controller = controller;
        this.setTitle("Sistema de Gestão de Biblioteca - Cadastro de Livro");
        this.setContentPane(CadLivro);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = formattedTextFieldTitulo.getText();
                String texto2 = formattedTextFieldTema.getText();
                String texto3 = formattedTextFieldAutor.getText();
                String texto4 = formattedTextFieldISBN.getText();
                JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!:\nTitulo: " + texto + "\nEmail: " + texto2 + "\nTelefone: " + texto3);
            }
        });
    }
    public static void main(String[] args) {
        new CadastroLivro();
    }

}
