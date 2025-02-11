package View;

import javax.swing.*;
import Controller.LivroController;
import Controller.UsuarioController;
import Model.LivroModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

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
    private LivroController livroController;

    public CadastroLivro(EntityManager em) {
        this.livroController = livroController;
        this.setTitle("Sistema de Gestão de Biblioteca - Cadastro de Livro");
        this.setContentPane(cadLivro);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        livroController = new LivroController(em); // Inicialize o usuarioController com EntityManager


        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = formattedTextFieldTitulo.getText();
                String tema = formattedTextFieldTema.getText();
                String autor = formattedTextFieldAutor.getText();
                String isbn = formattedTextFieldISBN.getText();
                Date dataPublicacao = Date.valueOf(formattedTextFieldDataPublicacao.getText());
                Integer quantidadeDisponivel = parseInt(formattedTextFieldQtdeDisponivel.getText());

                // Crie e preencha o modelo de livro
                LivroModel livro = new LivroModel();
                livro.setTitulo(titulo);
                livro.setTema(tema);
                livro.setAutor(autor);
                livro.setIsbn(isbn);
                livro.setDataPublicacao(dataPublicacao);
                livro.setQuantidadeDisponivel(parseInt(formattedTextFieldQtdeDisponivel.getText()));

                try {
                    // Salve o livro no banco de dados
                    livroController.salvar(livro);
                    JOptionPane.showMessageDialog(null, "Livro cadastrado:\nTitulo: " + titulo + "\nTema: " + tema + "\nAutor: " + autor + "\nISBN:" + isbn + "\nData de Publicacao:" + dataPublicacao+ "\nQuantidade Disponivel:"+ quantidadeDisponivel);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar o livro: " + ex.getMessage());
                }
                dispose(); // Fecha a janela de cadastro
            }
        });

    }
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        SwingUtilities.invokeLater(() -> new CadastrarEmprestimo(em));
    }
}
