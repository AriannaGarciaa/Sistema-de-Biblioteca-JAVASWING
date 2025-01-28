package View;

import Controller.EmprestimoController;
import Model.EmprestimoModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class EmprestimoView {
    private EmprestimoController emprestimoController = new EmprestimoController();
    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int opcao;
        do {
            System.out.println("\n--- Gestão de Empréstimos ---");
            System.out.println("1. Registrar Empréstimo");
            System.out.println("2. Listar Empréstimos");
            System.out.println("3. Devolver Livro");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    registrarEmprestimo();
                    break;
                case 2:
                    listarEmprestimos();
                    break;
                case 3:
                    devolverEmprestimo();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }

    private void registrarEmprestimo() {
        System.out.print("Digite o ID do Usuário: ");
        int usuarioId = scanner.nextInt();
        System.out.print("Digite o ID do Livro: ");
        int livroId = scanner.nextInt();

        emprestimoController.registrarEmprestimo(usuarioId, livroId);
    }

    private void listarEmprestimos() {
        List<EmprestimoModel> emprestimos = emprestimoController.listarEmprestimos();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\n--- Lista de Empréstimos ---");
        for (EmprestimoModel e : emprestimos) {
            System.out.println("ID: " + e.getId() +
                    ", Usuário ID: " + e.getUsuarioId() +
                    ", Livro ID: " + e.getLivroId() +
                    ", Data Empréstimo: " + sdf.format(e.getDataEmprestimo()) +
                    ", Data Devolução: " + (e.getDataDevolucao() != null ? sdf.format(e.getDataDevolucao()) : "N/A") +
                    ", Devolvido: " + (e.isDevolvido() ? "Sim" : "Não"));
        }
    }

    private void devolverEmprestimo() {
        System.out.print("Digite o ID do Empréstimo: ");
        int emprestimoId = scanner.nextInt();

        emprestimoController.devolverEmprestimo(emprestimoId);
    }
}
