package View;

import Controller.EmprestimoController;
import Controller.LivroController;
import Controller.UsuarioController;
import Model.EmprestimoModel;
import Model.LivroModel;
import Model.UsuarioModel;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class EditarEmprestimo extends JFrame {
    private JTextField textFieldUsuarioNome;
    private JTextField textFieldLivroTitlulo;
    private JFormattedTextField formattedTextFieldDataEmprestimo;
    private JFormattedTextField formattedTextFieldDataDevolucao;
    private JCheckBox checkBoxDevolvido;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JPanel panel;
    private EmprestimoController emprestimoController;
    private EmprestimoModel emprestimo;

    public EditarEmprestimo(EntityManager em, EmprestimoModel emprestimo) {
        this.emprestimoController = new EmprestimoController(em);
        this.emprestimo = emprestimo;

        this.setTitle("Editar Empréstimo");
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
        panel.add(new JLabel("Nome do Usuário:"), gbc);
        gbc.gridx = 1;
        textFieldUsuarioNome = new JTextField(15);
        textFieldUsuarioNome.setText(emprestimo.getUsuario().getNome());
        textFieldUsuarioNome.setEditable(false);
        panel.add(textFieldUsuarioNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Título do Livro:"), gbc);
        gbc.gridx = 1;
        textFieldLivroTitlulo = new JTextField(15);
        textFieldLivroTitlulo.setText(emprestimo.getLivro().getTitulo());
        textFieldLivroTitlulo.setEditable(false);
        panel.add(textFieldLivroTitlulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Data de Empréstimo (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataEmprestimo = createFormattedDateField();
        formattedTextFieldDataEmprestimo.setText(formatDate(emprestimo.getDataEmprestimo()));
        panel.add(formattedTextFieldDataEmprestimo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Data de Devolução Prevista (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataDevolucao = createFormattedDateField();
        formattedTextFieldDataDevolucao.setText(formatDate(emprestimo.getDataDevolucao()));
        panel.add(formattedTextFieldDataDevolucao, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        checkBoxDevolvido = new JCheckBox("Devolvido", emprestimo.isDevolvido());
        panel.add(checkBoxDevolvido, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        salvarButton = new JButton("Salvar");
        salvarButton.setBackground(Color.BLUE);
        salvarButton.setForeground(Color.WHITE);
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(Color.red);
        cancelarButton.setForeground(Color.WHITE);
        panel.add(salvarButton, gbc);
        gbc.gridx = 1;
        panel.add(cancelarButton, gbc);

        salvarButton.addActionListener(this::salvarAlteracoes);
        cancelarButton.addActionListener(e -> dispose());

        this.add(panel);
        this.setVisible(true);
    }

    private JFormattedTextField createFormattedDateField() {
        try {
            MaskFormatter dateMask = new MaskFormatter("##-##-####");
            dateMask.setPlaceholderCharacter('_');
            return new JFormattedTextField(dateMask);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao configurar máscara de data: " + ex.getMessage());
            return new JFormattedTextField();
        }
    }

    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    private void salvarAlteracoes(ActionEvent e) {
        emprestimo.setDevolvido(checkBoxDevolvido.isSelected());
        emprestimo.setDataEmprestimo(parseDate(formattedTextFieldDataEmprestimo.getText()));
        emprestimo.setDataDevolucao(parseDate(formattedTextFieldDataDevolucao.getText()));

        try {
            emprestimoController.atualizar(emprestimo);
            JOptionPane.showMessageDialog(null, "Empréstimo atualizado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar empréstimo: " + ex.getMessage());
        }
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            return new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
