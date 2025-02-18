package View;

import Controller.EmprestimoController;
import Model.EmprestimoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DevolverEmprestimo extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panel1;
    private JComboBox<String> comboBoxEmprestimos;
    private JFormattedTextField formattedTextFieldDataEmprestimo;
    private JFormattedTextField formattedTextFieldDataDevolucao;
    private JCheckBox checkBoxDevolvido;
    private JButton devolverButton;
    private EmprestimoController emprestimoController;
    private List<EmprestimoModel> emprestimosPendentes;

    public DevolverEmprestimo(EntityManager em) {
        this.setTitle("Devolução de Livro");
        this.emprestimoController = new EmprestimoController(em);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.setBackground(Color.cyan);

        panelPrincipal.add(new JLabel("Selecione o Empréstimo:"), gbc);
        gbc.gridx = 1;
        comboBoxEmprestimos = new JComboBox<>();
        panelPrincipal.add(comboBoxEmprestimos, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelPrincipal.add(new JLabel("Data de Empréstimo:"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataEmprestimo = createFormattedDateField();
        formattedTextFieldDataEmprestimo.setEditable(false);
        panelPrincipal.add(formattedTextFieldDataEmprestimo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelPrincipal.add(new JLabel("Data de Devolução:"), gbc);
        gbc.gridx = 1;
        formattedTextFieldDataDevolucao = createFormattedDateField();
        panelPrincipal.add(formattedTextFieldDataDevolucao, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        checkBoxDevolvido = new JCheckBox("Devolvido");
        panelPrincipal.add(checkBoxDevolvido, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        devolverButton = new JButton("Devolver");
        devolverButton.setBackground(Color.BLUE);
        devolverButton.setForeground(Color.WHITE);
        panelPrincipal.add(devolverButton, gbc);

        this.setContentPane(panelPrincipal);
        this.setVisible(true);

        carregarEmprestimosPendentes();

        comboBoxEmprestimos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = comboBoxEmprestimos.getSelectedIndex();
                if (selectedIndex >= 0) {
                    EmprestimoModel emprestimo = emprestimosPendentes.get(selectedIndex);
                    formattedTextFieldDataEmprestimo.setText(formatDate(emprestimo.getDataEmprestimo()));
                    formattedTextFieldDataDevolucao.setText(formatDate(emprestimo.getDataDevolucao()));
                    checkBoxDevolvido.setSelected(emprestimo.isDevolvido());
                }
            }
        });

        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = comboBoxEmprestimos.getSelectedIndex();
                if (selectedIndex >= 0) {
                    EmprestimoModel emprestimo = emprestimosPendentes.get(selectedIndex);
                    emprestimo.setDevolvido(true);

                    String dataEmprestimoStr = formattedTextFieldDataEmprestimo.getText();
                    String dataDevolucaoStr = formattedTextFieldDataDevolucao.getText();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    sdf.setLenient(false);

                    Date dataEmprestimo;
                    Date dataDevolucao;
                    try {
                        dataEmprestimo = new Date(sdf.parse(dataEmprestimoStr).getTime());
                        dataDevolucao = new Date(sdf.parse(dataDevolucaoStr).getTime());

                        if (dataDevolucao.before(dataEmprestimo)) {
                            JOptionPane.showMessageDialog(null, "A data de devolução deve ser posterior à data de empréstimo.");
                            return;
                        }

                        double multa = 0.0;
                        long diffInMillies = dataDevolucao.getTime() - dataEmprestimo.getTime();
                        long diffDays = diffInMillies / (24 * 60 * 60 * 1000);
                        if (diffDays > 14) {
                            long diasDeAtraso = diffDays - 14;
                            multa = diasDeAtraso * 1.0;
                        }

                        if (multa > 0) {
                            JOptionPane.showMessageDialog(null, "A devolução está atrasada. Multa: R$ " + multa);
                        }

                        emprestimoController.devolverEmprestimo(emprestimo.getId());

                        JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
                        dispose();

                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Formato de data inválido. Use o formato DD-MM-YYYY.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao devolver o livro: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um empréstimo válido.");
                }
            }
        });
    }

    private void carregarEmprestimosPendentes() {
        emprestimosPendentes = emprestimoController.listarEmprestimosPendentes(); // Método a ser implementado no EmprestimoController
        for (EmprestimoModel emprestimo : emprestimosPendentes) {
            comboBoxEmprestimos.addItem(emprestimo.getLivro().getTitulo() + " - " + emprestimo.getUsuario().getNome());
        }
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

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        SwingUtilities.invokeLater(() -> new DevolverEmprestimo(em));
    }
}
