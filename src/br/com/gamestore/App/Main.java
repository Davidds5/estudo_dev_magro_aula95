package br.com.gamestore.App;


import br.com.gamestore.Domain.Acessorio;
import br.com.gamestore.Domain.Cliente;
import br.com.gamestore.Domain.Console;
import br.com.gamestore.Domain.Jogo;
import br.com.gamestore.Service.ClienteService;
import br.com.gamestore.Service.ProdutoService;
import br.com.gamestore.Service.VendaService;
import br.com.gamestore.Util.Menu;

public class Main {
    public static void main(String[] args) {
        ProdutoService produtoService = new ProdutoService();
        ClienteService clienteService = new ClienteService();
        VendaService vendaService = new VendaService(produtoService);


        produtoService.adicionarProduto(new Console("C-PS5", "PlayStation 5", "Console PS5 - 825GB", 3999.00, 5, "Sony"));
        produtoService.adicionarProduto(new Jogo("J-GOW", "God of War Ragnarok", "Ação/Aventura", 249.00, 10, "PS5"));
        produtoService.adicionarProduto(new Acessorio("A-HX", "Headset HyperX", "Headset Gamer", 349.90, 8, "Headset"));
        produtoService.adicionarProduto(new Jogo("J-FH5", "Forza Horizon 5", "Corrida", 179.90, 6, "Xbox"));
        produtoService.adicionarProduto(new Console("C-SW", "Nintendo Switch", "Switch OLED", 2999.00, 3, "Nintendo"));


        clienteService.adicionarCliente(new Cliente("cli01", "David Silva", "david@email.com", "000.000.000-00"));


        Menu menu = new Menu(produtoService, clienteService, vendaService);
        menu.iniciar();
    }
}
