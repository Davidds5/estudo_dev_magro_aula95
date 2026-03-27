package br.com.gamestore.Util;

import br.com.gamestore.Domain.*;
import br.com.gamestore.Exceptions.EstoqueInsuficiente;
import br.com.gamestore.Exceptions.ProdutoNaoEncotrado;
import br.com.gamestore.Service.ClienteService;
import br.com.gamestore.Service.ProdutoService;
import br.com.gamestore.Service.VendaService;


import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final VendaService vendaService;

    public Menu(ProdutoService ps, ClienteService cs, VendaService vs) {
        this.produtoService = ps;
        this.clienteService = cs;
        this.vendaService = vs;
    }

    public void iniciar() {
        boolean running = true;
        while (running) {
            mostrarOpcoes();
            int op = lerInt("Escolha uma opção: ");
            try {
                switch (op) {
                    case 1: cadastrarProduto(); break;
                    case 2: listarProdutos(); break;
                    case 3: cadastrarCliente(); break;
                    case 4: criarCarrinhoEFazerCompra(); break;
                    case 5: relatorios(); break;
                    case 0: running = false; System.out.println("Saindo..."); break;
                    default: System.out.println("Opção inválida."); break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void mostrarOpcoes() {
        System.out.println("\n=== GAMESTORE ===");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("3. Cadastrar Cliente");
        System.out.println("4. Criar Carrinho e Finalizar Compra");
        System.out.println("5. Relatórios (vendas / estoque baixo)");
        System.out.println("0. Sair");
    }

    private void cadastrarProduto() {
        System.out.println("--- Cadastrar Produto ---");
        String codigo = lerString("Código único: ");
        String nome = lerString("Nome: ");
        String descricao = lerString("Descrição: ");
        double preco = lerDouble("Preço: ");
        int estoque = lerInt("Estoque: ");
        System.out.println("Categoria: 1-Jogo 2-Console 3-Acessório");
        int c = lerInt("Escolha: ");
        switch (c) {
            case 1:
                String plataforma = lerString("Plataforma: ");
                produtoService.adicionarProduto(new Jogo(codigo, nome, descricao, preco, estoque, plataforma));
                break;
            case 2:
                String fab = lerString("Fabricante: ");
                produtoService.adicionarProduto(new Console(codigo, nome, descricao, preco, estoque, fab));
                break;
            case 3:
                String tipo = lerString("Tipo acessório: ");
                produtoService.adicionarProduto(new Acessorio(codigo, nome, descricao, preco, estoque, tipo));
                break;
            default:
                System.out.println("Categoria inválida.");
                return;
        }
        System.out.println("Produto cadastrado!");
    }

    private void listarProdutos() {
        System.out.println("--- Produtos ---");
        List<Produto> ps = produtoService.listaProdutos();
        if (ps.isEmpty()) System.out.println("Nenhum produto cadastrado.");
        else ps.forEach(System.out::println);
    }

    private void cadastrarCliente() {
        System.out.println("--- Cadastrar Cliente ---");
        String id = UUID.randomUUID().toString().substring(0,8);
        String nome = lerString("Nome: ");
        String email = lerString("Email: ");
        String cpf = lerString("CPF: ");
        clienteService.adicionarCliente(new Cliente(id, nome, email, cpf));
        System.out.println("Cliente cadastrado com ID: " + id);
    }

    private void criarCarrinhoEFazerCompra() throws ProdutoNaoEncotrado, EstoqueInsuficiente {
        System.out.println("--- Criar Carrinho e Finalizar Compra ---");
        String clienteId = lerString("ID do cliente: ");
        Cliente cliente = clienteService.buscaPorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        Carrinho carrinho = new Carrinho(UUID.randomUUID().toString().substring(0,8), cliente);
        boolean adicionando = true;
        while (adicionando) {
            listarProdutos();
            String codigo = lerString("Código do produto para adicionar (ou 'fim' para finalizar): ");
            if ("fim".equalsIgnoreCase(codigo)) break;
            Produto p = produtoService.buscaProduto(codigo);
            int qtd = lerInt("Quantidade: ");
            if (qtd <= 0) { System.out.println("Quantidade inválida."); continue; }
            if (p.getEstoque() < qtd) {
                System.out.println("Estoque insuficiente. Disponível: " + p.getEstoque());
                continue;
            }
            carrinho.adicionarItem(p, qtd);
            System.out.println("Adicionado: " + p.getNome());
        }

        System.out.println("\n" + carrinho);
        String confirmar = lerString("Confirmar compra? (s/n): ");
        if (confirmar.equalsIgnoreCase("s")) {
            String nota = vendaService.finalizarVenda(carrinho);
            System.out.println("Compra finalizada!\n" + nota);
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    private void relatorios() {
        System.out.println("--- Relatórios ---");
        System.out.println("1. Histórico de vendas");
        System.out.println("2. Produtos com estoque baixo");
        int op = lerInt("Escolha: ");
        switch (op) {
            case 1:
                vendaService.getHistoricoVendas().forEach(System.out::println);
                break;
            case 2:
                int limite = lerInt("Limite de estoque para considerar baixo: ");
                produtoService.produtosComEstoqueBaixo(limite).forEach(System.out::println);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }


    private String lerString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }

    private double lerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String s = sc.nextLine().trim();
                return Double.parseDouble(s.replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }
}
