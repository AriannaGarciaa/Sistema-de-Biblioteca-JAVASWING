package View;

import jakarta.persistence.EntityManager;

import javax.swing.*;

public class CadastroLivro {
    private JPanel CadLivro;
    private JButton cadastrarButton;
    private JFormattedTextField formattedTextFieldTitulo;
    private JFormattedTextField formattedTextFieldTema;
    private JFormattedTextField formattedTextFieldAutor;
    private JFormattedTextField formattedTextFieldISBN;
    private JFormattedTextField formattedTextFieldQtdeDisponivel;
    private JFormattedTextField formattedTextFieldDataPublicacao;
    private JPanel cadLivro;

    public CadastroLivro(EntityManager em) {
    }
}
