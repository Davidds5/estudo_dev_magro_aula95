package br.com.gamestore.Service;

import br.com.gamestore.Domain.Produto;
import br.com.gamestore.Exceptions.ProdutoNaoEncotrado;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoService {
    private final List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }
    public List<Produto> listaProdutos(){
        return produtos;
    }
    public Produto buscaProduto(String codigo) throws ProdutoNaoEncotrado {
        Optional<Produto> opt = produtos.stream()
                .filter(p ->p.getCodigo().equals(codigo))
                .findFirst();
        if (!opt.isPresent())throw new ProdutoNaoEncotrado("Produto: "+codigo+" nao encontrado.");
        return opt.get();

    }
    public List<Produto> produtosComEstoqueBaixo(int limite) {
        List<Produto> res = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getEstoque() <= limite) {
                res.add(p);
            }
        }
        return res;
    }
}
