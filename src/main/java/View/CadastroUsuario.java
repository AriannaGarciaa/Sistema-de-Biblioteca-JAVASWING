package View;

import Controller.UsuarioController;
import Model.UsuarioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class CadastroUsuario extends JFrame {

    private JPanel jpanelUsuario;
    private JPanel panelUsuario;
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JTextField textFieldSexo;
    private JFormattedTextField formattdTextFieldTelefone;
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

        usuarioController = new UsuarioController(entityManager);

        try {
            MaskFormatter maskTelefone = new MaskFormatter("(##) #####-####");
            maskTelefone.setPlaceholderCharacter('_');
            formattdTextFieldTelefone = new JFormattedTextField(maskTelefone);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Erro ao aplicar máscara no telefone.");
            e.printStackTrace();
        }

        textFieldEmail = new JTextField();
        ((AbstractDocument) textFieldEmail.getDocument()).setDocumentFilter(new EmailDocumentFilter());

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String email = textFieldEmail.getText();
                String sexo = textFieldSexo.getText();
                String telefone = formattdTextFieldTelefone.getText();

                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Email inválido. Por favor, insira um email válido.");
                    return;
                }

                UsuarioModel usuario = new UsuarioModel(nome, sexo, telefone, email);

                try {
                    usuarioController.salvar(usuario); // Salve o usuário
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado:\nNome: " + nome + "\nEmail: " + email + "\nTelefone: " + telefone);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o usuário: " + ex.getMessage());
                }
                dispose();
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; // Expressão regular para email
        return email.matches(emailRegex);
    }

    private class EmailDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            String regex = "[a-zA-Z0-9@._-]+";
            if (string.matches(regex)) {
                super.insertString(fb, offset, string, attr);
            } else {
                JOptionPane.showMessageDialog(null, "Caracteres inválidos no email.");
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String regex = "[a-zA-Z0-9@._-]+";
            if (text.matches(regex)) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                JOptionPane.showMessageDialog(null, "Caracteres inválidos no email.");
            }
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        new CadastroUsuario(em);
    }
}
