package View;

import Controller.UsuarioController;
import Model.UsuarioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class CadastroUsuario extends JFrame {
    private JPanel panelUsuario;
    private JPanel jpanelUsuario;
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JTextField textFieldSexo;
    private JFormattedTextField formattedTextFieldTelefone;
    private JButton buttonCadastrar;
    private JButton cancelarButton;
    private JLabel labelNome;
    private JLabel labelEmail;
    private JLabel labelTelefone;
    private JLabel labelSexo;
    private UsuarioController usuarioController;

    public CadastroUsuario(EntityManager entityManager) {
        this.setTitle("Cadastro de Usuario");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.jpanelUsuario = new JPanel(new GridBagLayout());
        this.setContentPane(jpanelUsuario);

        jpanelUsuario.setBackground(Color.cyan);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        labelNome = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        jpanelUsuario.add(labelNome, gbc);

        textFieldNome = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        jpanelUsuario.add(textFieldNome, gbc);

        labelEmail = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        jpanelUsuario.add(labelEmail, gbc);

        textFieldEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        jpanelUsuario.add(textFieldEmail, gbc);

        labelSexo = new JLabel("Sexo:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        jpanelUsuario.add(labelSexo, gbc);

        textFieldSexo = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        jpanelUsuario.add(textFieldSexo, gbc);

        labelTelefone = new JLabel("Telefone:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        jpanelUsuario.add(labelTelefone, gbc);

        formattedTextFieldTelefone = createFormattedTelefoneField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        jpanelUsuario.add(formattedTextFieldTelefone, gbc);

        buttonCadastrar = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        buttonCadastrar.setBackground(Color.BLUE);
        buttonCadastrar.setForeground(Color.WHITE);
        jpanelUsuario.add(buttonCadastrar, gbc);

        cancelarButton = new JButton("Cancelar");
        gbc.gridx = 1;
        gbc.gridy = 4;
        buttonCadastrar.setBackground(Color.red);
        buttonCadastrar.setForeground(Color.WHITE);
        jpanelUsuario.add(cancelarButton, gbc);

        this.setVisible(true);

        usuarioController = new UsuarioController(entityManager);

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String email = textFieldEmail.getText();
                String sexo = textFieldSexo.getText();
                String telefone = formattedTextFieldTelefone.getText();

                UsuarioModel usuario = new UsuarioModel(nome, sexo, telefone, email);

                try {
                    usuarioController.salvar(usuario);
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado:\nNome: " + nome + "\nEmail: " + email + "\nTelefone: " + telefone);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o usuário: " + ex.getMessage());
                }
                dispose();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private JFormattedTextField createFormattedTelefoneField() {
        try {
            MaskFormatter mask = new MaskFormatter("(##) #####-####");
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao configurar máscara de telefone: " + ex.getMessage());
            return new JFormattedTextField();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        new CadastroUsuario(em);
    }
}
