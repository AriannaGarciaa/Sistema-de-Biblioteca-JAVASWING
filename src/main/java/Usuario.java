import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usuario extends JFrame {
    private JPanel panelPrincipal;
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JTextField textFieldTelefone;
    private JButton buttonCadastrar;
    private JLabel labelNome;
    private JLabel labelEmail;
    private JLabel labelTelefone;

    public Usuario() {
        this.setTitle("Sistema de Gestão de Biblioteca");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.setVisible(true);

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String email = textFieldEmail.getText();
                String telefone = textFieldTelefone.getText();

                JOptionPane.showMessageDialog(null, "Usuário cadastrado:\nNome: " + nome + "\nEmail: " + email + "\nTelefone: " + telefone);
            }
        });
    }

    private void createUIComponents() {
        // Personalização de componentes (caso necessário)
    }

    public static void main(String[] args) {
        new Usuario();
    }
}
