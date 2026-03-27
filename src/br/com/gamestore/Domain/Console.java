package br.com.gamestore.Domain;

public class Console extends Produto{
    private String fabricante;

    public Console(String codigo, String nome, String descricao, double preco, int estoque, String fabricante) {
        super(codigo, nome, descricao, preco, estoque, Categoria.CONSOLE);
        this.fabricante = fabricante;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public String toString() {
        return super.toString() + "Console - "+fabricante+")";
    }
}
