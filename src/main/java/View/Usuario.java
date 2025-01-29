package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usuario extends JFrame {
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

    public Usuario() {
        this.setTitle("Sistema de Gestão de Biblioteca");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(jpanelUsuario);
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


    public static void main(String[] args) {
        new Usuario();
    }
}
