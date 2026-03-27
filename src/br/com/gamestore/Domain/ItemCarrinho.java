package br.com.gamestore.Domain;

public class ItemCarrinho {
    private final Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade){
        this.produto = produto;

    }

    public Produto getProduto() {
        return produto;
    }
    public int getQuantidade(){
        return quantidade;

    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double subTotal(){
        return produto.getPreco() * quantidade;
    }
    public String toString(){
        return String.format("%s x%d - R$ %.2f", produto.getNome(), quantidade, subTotal());
    }

}
