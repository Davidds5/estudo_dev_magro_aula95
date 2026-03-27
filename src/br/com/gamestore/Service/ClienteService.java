package br.com.gamestore.Service;

import br.com.gamestore.Domain.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final List<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(Cliente c){
        clientes.add(c);
    }
    public List<Cliente> listaCliente(){
        return clientes;

    }
    public Cliente buscaPorId(String id){
        Optional<Cliente> opt = clientes.stream().filter(c ->c.getId().equals(id)).findFirst();
        return opt.orElseGet(null);
    }
}
