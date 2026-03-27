package br.com.gamestore.Service;



import br.com.gamestore.Domain.Carrinho;
import br.com.gamestore.Domain.ItemCarrinho;
import br.com.gamestore.Domain.Produto;
import br.com.gamestore.Exceptions.EstoqueInsuficiente;
import br.com.gamestore.Exceptions.ProdutoNaoEncotrado;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VendaService {
    private final ProdutoService produtoService;
    private final List<String> vendas = new ArrayList<>(); // simples histórico (nota/receipt)

    public VendaService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public String finalizarVenda(Carrinho carrinho) throws ProdutoNaoEncotrado, EstoqueInsuficiente {
        if (carrinho.estaVazio()) throw new IllegalArgumentException("Carrinho vazio.");

        // Verifica estoque
        for (ItemCarrinho item : carrinho.getItens()) {
                Produto p = produtoService.buscaProduto(item.getProduto().getCodigo());
            if (p.getEstoque() < item.getQuantidade()) {
                throw new EstoqueInsuficiente("Estoque insuficiente para " + p.getNome());
            }
        }

        // Efetua baixa de estoque
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto p = produtoService.buscaProduto(item.getProduto().getCodigo());
            p.reduzirEstoque(item.getQuantidade());
        }

        // Gera nota
        String nota = gerarNota(carrinho);
        vendas.add(nota);

        // Adiciona ao histórico do cliente
        carrinho.getCliente().adicionarHistorico(nota);

        return nota;
    }

    private String gerarNota(Carrinho carrinho) {
        String idVenda = UUID.randomUUID().toString().substring(0, 8);
        StringBuilder sb = new StringBuilder();
        sb.append("=== NOTA DE VENDA ===\n");
        sb.append("ID Venda: ").append(idVenda).append("\n");
        sb.append("Cliente: ").append(carrinho.getCliente().getNome()).append("\n");
        sb.append("Data: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
        sb.append("-------------------------\n");
        for (ItemCarrinho ic : carrinho.getItens()) {
            sb.append(ic.getProduto().getNome()).append(" x").append(ic.getQuantidade())
                    .append(" = R$ ").append(String.format("%.2f", ic.subTotal())).append("\n");
        }
        sb.append("-------------------------\n");
        sb.append(String.format("TOTAL: R$ %.2f\n", carrinho.total()));
        sb.append("=========================\n");
        return sb.toString();
    }

    public List<String> getHistoricoVendas() {
        return vendas;
    }
}
