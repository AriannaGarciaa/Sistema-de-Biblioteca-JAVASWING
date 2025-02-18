package View;

import Controller.LivroController;
<<<<<<< HEAD
import Model.EmprestimoModel;
=======
>>>>>>> origin/main
import Model.LivroModel;
import Repository.LivroRepository;
import jakarta.persistence.EntityManager;

<<<<<<< HEAD
import java.awt.*;
=======
>>>>>>> origin/main
import java.lang.String;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscarLivro extends JFrame {
    private JPanel panelPrincipal;
    private JButton buttonBuscar;
    private JTextField textFieldBusca;
    private JTable tableBuscaLivro;
    private JButton removerButton;
    private JScrollPane scrollPaneLIvro;
<<<<<<< HEAD
    private JButton editarButton;
=======
>>>>>>> origin/main

    private LivroController livroController;

    public BuscarLivro(EntityManager em) {
<<<<<<< HEAD
        this.setTitle("Lista de Livros");
=======
        this.setTitle("Sistema de Gestão de Biblioteca");
>>>>>>> origin/main
        this.livroController = new LivroController(em);
        LivroModeloDeTabela livroModeloDeTabela = new LivroModeloDeTabela(em);
        tableBuscaLivro.setModel(livroModeloDeTabela);
        tableBuscaLivro.setAutoCreateRowSorter(true);
        this.setContentPane(panelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
<<<<<<< HEAD
        panelPrincipal.setBackground(Color.cyan);
=======
>>>>>>> origin/main

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaLivro.getSelectedRow();
                if (linhaSelecionada != -1) {
                    Long idLivroSelecionado = Long.parseLong(tableBuscaLivro.getValueAt(linhaSelecionada, 0).toString());
                    try {
<<<<<<< HEAD
                        livroController.deletarLivro(idLivroSelecionado.intValue());
                        JOptionPane.showMessageDialog(null, "Livro Removido com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover o livro: " + ex.getMessage());
=======
                        JOptionPane.showMessageDialog(null, "Livro Removido" + livroController.deletarLivro(idLivroSelecionado.intValue()));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
>>>>>>> origin/main
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione o registro que deseja remover");
                }
            }
        });
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buscaId = textFieldBusca.getText();
                if (!buscaId.isEmpty()) {
                    try {
                        int idLivro = Integer.parseInt(buscaId);
                        LivroModel livro = livroController.buscarPorId(idLivro);
                        if (livro != null) {
                            livroModeloDeTabela.atualizarTabelaComLivro(livro);
                        } else {
                            JOptionPane.showMessageDialog(null, "Livro não encontrado!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Digite o ID do livro para buscar.");
                }
            }
        });
<<<<<<< HEAD
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaLivro.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um livro para editar!");
                    return;
                }
                int idLivro = (int) tableBuscaLivro.getValueAt(linhaSelecionada, 0);
                LivroModel livro = livroController.buscarPorId(idLivro);

                if (livro != null) {
                    new EditarLivro(em, livro);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar Tela de Edição de livro!");
                }
            }
        });
=======
>>>>>>> origin/main
    }

    private static class LivroModeloDeTabela extends AbstractTableModel {
        private final LivroRepository livroRepository;
        private final String[] COLUMNS = new String[]{"Id", "Título", "Tema", "Autor", "ISBN", "Data de Publicação", "Quantidade Disponível"};
        private List<LivroModel> listaDeLivros;

        public LivroModeloDeTabela(EntityManager em) {
            this.livroRepository = new LivroRepository(em);
            this.listaDeLivros = livroRepository.listarTodos();
        }

        @Override
        public int getRowCount() {
            return listaDeLivros.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listaDeLivros.get(rowIndex).getId();
                case 1 -> listaDeLivros.get(rowIndex).getTitulo();
                case 2 -> listaDeLivros.get(rowIndex).getTema();
                case 3 -> listaDeLivros.get(rowIndex).getAutor();
                case 4 -> listaDeLivros.get(rowIndex).getIsbn();
                case 5 -> listaDeLivros.get(rowIndex).getDataPublicacao();
                case 6 -> listaDeLivros.get(rowIndex).getQuantidadeDisponivel();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMNS[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }

        public void atualizarTabelaComLivro(LivroModel livro) {
            this.listaDeLivros = List.of(livro);
            fireTableDataChanged();
        }

    }
}
