package View;

import Controller.UsuarioController;
import Model.LivroModel;
import Model.UsuarioModel;
import Repository.UsuarioRepository;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.List;

public class BuscarUsuario extends JFrame {
    private JPanel panelPrincipal;
    private JButton buttonBuscar;
    private JTextField textFieldBusca;
    private JTable tableBuscaUsuario;
    private JScrollPane scrollPaneUsuario;
    private JButton removerButton;
    private JButton editarButton;

    private UsuarioController usuarioController;

    public BuscarUsuario(EntityManager em) {
        this.setTitle("Lista de Usuarios");
        this.usuarioController = new UsuarioController(em);
        UsuarioModeloDeTabela usuarioModeloDeTabela = new UsuarioModeloDeTabela(em);
        tableBuscaUsuario.setModel(usuarioModeloDeTabela);
        tableBuscaUsuario.setAutoCreateRowSorter(true);
        this.setContentPane(panelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        panelPrincipal.setBackground(Color.cyan);

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaUsuario.getSelectedRow();
                if (linhaSelecionada != -1) {
                    Long idUsuarioSelecionado = Long.parseLong(tableBuscaUsuario.getValueAt(linhaSelecionada, 0).toString());
                    try {
                        usuarioController.deletarUsuario(idUsuarioSelecionado.intValue());
                        usuarioModeloDeTabela.atualizarTabela();
                        JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover usuário: " + ex.getMessage());
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
                        int idUsuario = Integer.parseInt(buscaId);
                        UsuarioModel usuario = usuarioController.buscarUsuarioPorId(idUsuario);
                        if (usuario != null) {
                            usuarioModeloDeTabela.atualizarTabelaComUsuario(usuario);
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Digite o ID do usuário para buscar.");
                }
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaUsuario.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um usuario para editar!");
                    return;
                }
                int idLivro = (int) tableBuscaUsuario.getValueAt(linhaSelecionada, 0);
                UsuarioModel usuario = usuarioController.buscarPorId(idLivro);

                if (usuario != null) {
                    new EditarUsuario(em, usuario);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar Tela de Edição de empréstimo!");
                }
            }
        });
    }

    private static class UsuarioModeloDeTabela extends AbstractTableModel {
        private final UsuarioRepository usuarioRepository;
        private final String[] COLUMNS = new String[]{"Id", "Nome", "Telefone", "Email", "Sexo"};
        private List<UsuarioModel> listaDeUsuarios;

        public UsuarioModeloDeTabela(EntityManager em) {
            this.usuarioRepository = new UsuarioRepository(em);
            this.listaDeUsuarios = usuarioRepository.listarTodos();
        }

        @Override
        public int getRowCount() {
            return listaDeUsuarios.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listaDeUsuarios.get(rowIndex).getId();
                case 1 -> listaDeUsuarios.get(rowIndex).getNome();
                case 2 -> listaDeUsuarios.get(rowIndex).getCelular();
                case 3 -> listaDeUsuarios.get(rowIndex).getEmail();
                case 4 -> listaDeUsuarios.get(rowIndex).getSexo();
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

        public void atualizarTabela() {
            this.listaDeUsuarios = usuarioRepository.listarTodos();
            fireTableDataChanged();
        }

        public void atualizarTabelaComUsuario(UsuarioModel usuario) {
            this.listaDeUsuarios = List.of(usuario);
            fireTableDataChanged();
        }

    }
}
