package View;

import Controller.UsuarioController;
import Model.UsuarioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroUsuario extends JFrame {
    private JPanel jpanelUsuario;
    private JPanel panelUsuario;
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JTextField textFieldTelefone;
    private JTextField textFieldSexo;
    private JButton buttonCadastrar;
    private JLabel labelNome;
    private JLabel labelEmail;
    private JLabel labelTelefone;
    private JLabel labelSexo;
    private UsuarioController usuarioController;

    public CadastroUsuario(EntityManager entityManager) {
        this.setTitle("Sistema de Gestão de Biblioteca");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(jpanelUsuario);
        this.setVisible(true);

        usuarioController = new UsuarioController(entityManager); // Inicialize o usuarioController com EntityManager

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String email = textFieldEmail.getText();
                String telefone = textFieldTelefone.getText();
                UsuarioModel usuario = new UsuarioModel(nome, email, telefone, textFieldSexo.getText()); // Inicialize o usuario

                try {
                    usuarioController.salvar(usuario); // Salve o usuário
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado:\nNome: " + nome + "\nEmail: " + email + "\nTelefone: " + telefone);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        new CadastroUsuario(em);
    }
}
