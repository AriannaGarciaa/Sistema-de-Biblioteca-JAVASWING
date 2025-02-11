package View;

import Controller.EmprestimoController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DevolverEmprestimo extends JFrame {
    private JPanel panelPrincipal;
    private JTextField textFieldEmprestimoId;
    private JCheckBox checkBoxDevolvido;
    private JButton devolverButton;
    private EmprestimoController emprestimoController;
    private JPanel panel1;

    public DevolverEmprestimo(EntityManager em) {
        this.setTitle("Devolução de Livro");
        this.emprestimoController = new EmprestimoController(em);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.setSize(400, 200);
        this.setVisible(true);

        // Definindo o comportamento do botão de devolução
        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtém o ID do empréstimo a ser devolvido
                    int emprestimoId = Integer.parseInt(textFieldEmprestimoId.getText());

                    // Atualiza o status do empréstimo como devolvido
                    emprestimoController.devolverEmprestimo(emprestimoId);

                    // Marca o checkbox "Devolvido" como verdadeiro (livro foi devolvido)
                    checkBoxDevolvido.setSelected(true);

                    JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
                    dispose();  // Fecha o formulário após devolução

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao devolver o livro: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        SwingUtilities.invokeLater(() -> new DevolverEmprestimo(em));
    }
}
