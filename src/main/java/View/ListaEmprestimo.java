package View;

import Controller.EmprestimoController;
import Model.EmprestimoModel;
import Repository.EmprestimoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaEmprestimo extends JFrame {
    private JPanel panelPrincipal;
    private JButton buttonBuscar;
    private JTextField textFieldBusca;
    private JTable tableBuscaEmprestimo;
    private JScrollPane scrollPaneEmprestimo;
    private JButton editarButton;

    private EmprestimoController emprestimoController;
    private EmprestimoModeloDeTabela emprestimoModeloDeTabela;

    public ListaEmprestimo(EntityManager em) {
        this.setTitle("Lista de Emprestimos");
        this.emprestimoController = new EmprestimoController(em);
        this.emprestimoModeloDeTabela = new EmprestimoModeloDeTabela(em);

        tableBuscaEmprestimo.setModel(emprestimoModeloDeTabela);
        tableBuscaEmprestimo.setAutoCreateRowSorter(true);
        this.setContentPane(panelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        panelPrincipal.setBackground(Color.cyan);

        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buscaId = textFieldBusca.getText();
                if (!buscaId.isEmpty()) {
                    try {
                        int idEmprestimo = Integer.parseInt(buscaId);
                        EmprestimoModel emprestimo = emprestimoController.buscarPorId(idEmprestimo);
                        if (emprestimo != null) {
                            emprestimoModeloDeTabela.atualizarTabelaComEmprestimo(emprestimo);
                        } else {
                            JOptionPane.showMessageDialog(null, "Empréstimo não encontrado!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Digite o ID do empréstimo para buscar.");
                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaEmprestimo.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um empréstimo para editar!");
                    return;
                }
                int idEmprestimo = (int) tableBuscaEmprestimo.getValueAt(linhaSelecionada, 0);
                EmprestimoModel emprestimo = emprestimoController.buscarPorId(idEmprestimo);

                if (emprestimo != null) {
                    new EditarEmprestimo(em, emprestimo);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar Tela de Edição de empréstimo!");
                }
            }
        });

    }


    private static class EmprestimoModeloDeTabela extends AbstractTableModel {
        private final EmprestimoRepository emprestimoRepository;
        private final String[] COLUMNS = {"Id", "Usuário", "Livro", "Data de Empréstimo", "Data de Devolução", "Devolvido"};
        private List<EmprestimoModel> listaDeEmprestimos;
        private final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

        public EmprestimoModeloDeTabela(EntityManager em) {
            this.emprestimoRepository = new EmprestimoRepository(em);
            this.listaDeEmprestimos = emprestimoRepository.listarTodos();
        }

        @Override
        public int getRowCount() {
            return listaDeEmprestimos.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            EmprestimoModel emprestimo = listaDeEmprestimos.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> emprestimo.getId();
                case 1 -> emprestimo.getUsuario().getNome();
                case 2 -> emprestimo.getLivro().getTitulo();
                case 3 -> formatarData(emprestimo.getDataEmprestimo());
                case 4 -> formatarData(emprestimo.getDataDevolucao());
                case 5 -> emprestimo.isDevolvido() ? "Sim" : "Não";
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMNS[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }

        private String formatarData(java.util.Date data) {
            return (data != null) ? formatoData.format(data) : "N/A";
        }

        public void atualizarTabelaComEmprestimo(EmprestimoModel emprestimo) {
            this.listaDeEmprestimos = List.of(emprestimo);
            fireTableDataChanged();
        }
    }
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
        EntityManager em = emf.createEntityManager();
        SwingUtilities.invokeLater(() -> new ListaEmprestimo(em));
    }
}
