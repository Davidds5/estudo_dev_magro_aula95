package br.com.gamestore.Domain;

public abstract class Produto {
    private final String codigo;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private final Categoria categoria;

    public Produto(String codigo, String nome,String descricao,  double preco, int estoque, Categoria categoria){
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;

    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }
    public Categoria getCategoria(){
        return categoria;
    }
    public void reduzirEstoque(int qtd){
        this.estoque -= qtd;

    }

    public String toString(){
        return String.format("[%s] %s - R$ %.2f - estoque: %d - %s", codigo, nome, preco, estoque, categoria);

    }
}
