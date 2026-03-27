package br.com.gamestore.Domain;

import br.com.gamestore.Domain.Cliente;
import br.com.gamestore.Domain.ItemCarrinho;
import br.com.gamestore.Domain.Produto;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private final String id;
    private final Cliente cliente;
    private final List<ItemCarrinho> itens = new ArrayList<>();

    public Carrinho(String id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemCarrinho> getItens() { return itens; }

    public void adicionarItem(Produto produto, int qtd) {
        for (ItemCarrinho ic : itens) {
            if (ic.getProduto().getCodigo().equals(produto.getCodigo())) {
                ic.setQuantidade(ic.getQuantidade() + qtd);
                return;
            }
        }
        itens.add(new ItemCarrinho(produto, qtd));
    }

    public void removerItem(String codigoProduto) {
        itens.removeIf(ic -> ic.getProduto().getCodigo().equals(codigoProduto));
    }

    public double total() {
        return itens.stream().mapToDouble(ItemCarrinho::subTotal).sum();
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Carrinho: " + id + " - Cliente: " + cliente.getNome() + "\n");
        for (ItemCarrinho ic : itens) sb.append(ic).append("\n");
        sb.append(String.format("Total: R$ %.2f", total()));
        return sb.toString();
    }
}
