package View;

import Controller.UsuarioController;

import java.util.Scanner;

public class UsuarioView {

    private final UsuarioController usuarioController;
    private int id;

    public UsuarioView() {
        this.usuarioController = new UsuarioController();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Sistema de Gestão de Biblioteca: Usuários ---");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Buscar Usuário por ID");
            System.out.println("4. Atualizar Usuário");
            System.out.println("5. Deletar Usuário");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> cadastrarUsuario(scanner);
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuarioPorId(scanner);
                case 4 -> atualizarUsuario(scanner);
                case 5 -> deletarUsuario(scanner);
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private void cadastrarUsuario(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Sexo: ");
        String sexo = scanner.nextLine();
        System.out.print("Celular: ");
        String celular = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        usuarioController.criarUsuario(nome, sexo, celular, email);
    }

    private void listarUsuarios() {
        System.out.println("\n--- Lista de Usuários ---");
        for (UsuarioView usuario : usuarioController.listarUsuarios()) {
            System.out.println(usuario);
        }
    }

    private void buscarUsuarioPorId(Scanner scanner) {
        System.out.print("Digite o ID do usuário: ");
        id = (int) scanner.nextLong();
        scanner.nextLine();

        UsuarioView usuario = usuarioController.buscarUsuarioPorId(id);
        if (usuario != null) {
            System.out.println(usuario);
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void atualizarUsuario(Scanner scanner) {
        System.out.print("Digite o ID do usuário: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha

        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo Sexo: ");
        String sexo = scanner.nextLine();
        System.out.print("Novo Celular: ");
        String celular = scanner.nextLine();
        System.out.print("Novo Email: ");
        String email = scanner.nextLine();

        usuarioController.atualizarUsuario(id, nome, sexo, celular, email);
    }

    private void deletarUsuario(Scanner scanner) {
        System.out.print("Digite o ID do usuário: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        usuarioController.deletarUsuario(id);
    }
}



