package View;

import Controller.UsuarioController;
import Model.UsuarioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditarUsuario extends JFrame {
    private JTextField textFieldNome;
    private JTextField textFieldSexo;
    private JTextField textFieldCelular;
    private JTextField textFieldEmail;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JPanel panel;
    private UsuarioController usuarioController;
    private UsuarioModel usuario;

    public EditarUsuario(EntityManager em, UsuarioModel usuario) {
        this.usuarioController = new UsuarioController(em);
        this.usuario = usuario;

        this.setTitle("Editar Usuário");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.setBackground(Color.cyan);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        textFieldNome = new JTextField(15);
        textFieldNome.setText(usuario.getNome());
        panel.add(textFieldNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        textFieldSexo = new JTextField(15);
        textFieldSexo.setText(usuario.getSexo());
        panel.add(textFieldSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Celular:"), gbc);
        gbc.gridx = 1;
        textFieldCelular = new JTextField(15);
        textFieldCelular.setText(usuario.getCelular());
        panel.add(textFieldCelular, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        textFieldEmail = new JTextField(15);
        textFieldEmail.setText(usuario.getEmail());
        panel.add(textFieldEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        salvarButton = new JButton("Salvar");
        salvarButton.setBackground(Color.BLUE);
        salvarButton.setForeground(Color.WHITE);
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(Color.RED);
        cancelarButton.setForeground(Color.WHITE);
        panel.add(salvarButton, gbc);
        gbc.gridx = 1;
        panel.add(cancelarButton, gbc);

        salvarButton.addActionListener(this::salvarAlteracoes);
        cancelarButton.addActionListener(e -> dispose());

        this.add(panel);
        this.setVisible(true);
    }

    private void salvarAlteracoes(ActionEvent e) {
        usuario.setNome(textFieldNome.getText().trim());
        usuario.setSexo(textFieldSexo.getText().trim());
        usuario.setCelular(textFieldCelular.getText().trim());
        usuario.setEmail(textFieldEmail.getText().trim());

        try {
            usuarioController.atualizarUsuario(usuario.getId(), usuario.getNome(), usuario.getSexo(), usuario.getCelular(), usuario.getEmail());
            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        UsuarioModel usuario = em.find(UsuarioModel.class, 1); // Carregue o usuário com ID 1, por exemplo
        SwingUtilities.invokeLater(() -> new EditarUsuario(em, usuario));
    }
}
