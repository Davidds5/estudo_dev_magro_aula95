package br.com.gamestore.Domain;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private final String id;
    private String nome;
    private String email;
    private String cpf;
    private final List<String> historicoDeCompras = new ArrayList<>();


    public Cliente(String id, String nome, String email, String cpf){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }
    public String getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getEmail(){
        return email;

    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getCpf(){
        return cpf = cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public List<String> getHistoricoDeCompras(){
        return historicoDeCompras;
    }
    public void adicionarHistorico(String nota){
        historicoDeCompras.add(nota);

    }
    public String toString(){
        return String.format("[%s] %s - %s - CPF:%s", id, nome, email, cpf);
    }
}