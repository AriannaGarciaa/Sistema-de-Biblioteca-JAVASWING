package View;

import Controller.EmprestimoController;
import Controller.LivroController;
import Controller.UsuarioController;
import Model.EmprestimoModel;
import Model.LivroModel;
import Model.UsuarioModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public class CadastrarEmprestimo extends JFrame {
    private JTextField textFieldUsuarioId;
    private JTextField textFieldLivroId;
    private JTextField formattedTextFieldDataEmprestimo;
    private JTextField formattedTextFieldDataDevolucao;
    private JCheckBox checkBoxDevolvido;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JPanel panel1;
    private EmprestimoController emprestimoController;
    private UsuarioController usuarioController;
    private LivroController livroController;

    public CadastrarEmprestimo(EntityManager em) {
        this.setTitle("Cadastro de Empréstimo");
        this.setContentPane(panel1);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        // Inicializa os controladores
        this.emprestimoController = new EmprestimoController(em);
        this.usuarioController = new UsuarioController(em);
        this.livroController = new LivroController(em);

        try{
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');

            formattedTextFieldDataDevolucao = new JFormattedTextField(maskData);
            formattedTextFieldDataEmprestimo = new JFormattedTextField(maskData);
        }catch (ParseException e){
            JOptionPane.showMessageDialog(null, "Erro ao aplicar mascara nos campos de data. ");
            e.printStackTrace();
        }

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioIdStr = textFieldUsuarioId.getText();
                String livroIdStr = textFieldLivroId.getText();

                // Validação simples dos campos de ID
                if (usuarioIdStr.isEmpty() || livroIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
                    return;
                }

                int usuarioId, livroId;
                try {
                    usuarioId = parseInt(usuarioIdStr);
                    livroId = parseInt(livroIdStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "IDs de usuário e livro devem ser números válidos.");
                    return;
                }

                // Verificação se o usuário já pegou 5 livros emprestados
                if (usuarioController.contarLivrosEmprestados(usuarioId) >= 5) {
                    JOptionPane.showMessageDialog(null, "O usuário já pegou 5 livros emprestados!");
                    return;
                }

                // Verificando se o livro está disponível
                LivroModel livro = livroController.buscarPorId(livroId);
                if (livro == null || livro.getQuantidadeDisponivel() <= 0) {
                    JOptionPane.showMessageDialog(null, "Não há exemplares disponíveis desse livro.");
                    return;
                }

                // Validação das datas
                String dataEmprestimoStr = formattedTextFieldDataEmprestimo.getText();
                String dataDevolucaoStr = formattedTextFieldDataDevolucao.getText();
                Date dataEmprestimo = null;
                Date dataDevolucao = null;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);

                try {
                    if (!dataEmprestimoStr.isEmpty()) {
                        dataEmprestimo = new Date(sdf.parse(dataEmprestimoStr).getTime());
                    }
                    if (!dataDevolucaoStr.isEmpty()) {
                        dataDevolucao = new Date(sdf.parse(dataDevolucaoStr).getTime());
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato de data inválido. Use o formato YYYY-MM-DD.");
                    return;
                }

                // Verifica se a data de devolução é posterior à data de empréstimo
                if (dataEmprestimo != null && dataDevolucao != null && dataDevolucao.before(dataEmprestimo)) {
                    JOptionPane.showMessageDialog(null, "A data de devolução deve ser posterior à data de empréstimo.");
                    return;
                }

                boolean devolvido = checkBoxDevolvido.isSelected();

                // Criar objeto Emprestimo
                UsuarioModel usuario = new UsuarioModel();
                usuario.setId(usuarioId);
                livro.setId(livroId);

                EmprestimoModel emprestimo = new EmprestimoModel();
                emprestimo.setUsuario(usuario);
                emprestimo.setLivro(livro);
                emprestimo.setDataEmprestimo(dataEmprestimo);
                emprestimo.setDataDevolucao(dataDevolucao);
                emprestimo.setDevolvido(devolvido);

                // Cálculo de multa por atraso
                double multa = 0.0;
                if (dataDevolucao != null && dataDevolucao.after(dataEmprestimo)) {
                    long diffInMillies = dataDevolucao.getTime() - dataEmprestimo.getTime();
                    long diffDays = diffInMillies / (24 * 60 * 60 * 1000); // Convertendo para dias

                    // Considerando que o prazo máximo de devolução é de 14 dias
                    if (diffDays > 14) {
                        // Se a devolução ultrapassar 14 dias, calcula a multa
                        long diasDeAtraso = diffDays - 14;
                        multa = diasDeAtraso * 1.0;  // R$1 por dia de atraso
                    }
                }

                // Exibe a multa, se houver
                if (multa > 0) {
                    JOptionPane.showMessageDialog(null, "A devolução está atrasada. Multa: R$ " + multa);
                }

                // Salvar o empréstimo no banco de dados
                try {
                    emprestimoController.salvar(emprestimo);
                    livroController.atualizarQuantidadeDisponivel(livroId, livro.getQuantidadeDisponivel() - 1);
                    JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar empréstimo: " + ex.getMessage());
                }

                dispose(); // Fecha a janela
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela
            }
        });
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        SwingUtilities.invokeLater(() -> new CadastrarEmprestimo(em));
    }
}
