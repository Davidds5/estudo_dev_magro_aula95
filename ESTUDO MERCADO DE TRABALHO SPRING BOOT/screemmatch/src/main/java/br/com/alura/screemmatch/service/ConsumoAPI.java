package br.com.alura.screemmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obterDados(String endereco){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                // endereco de requisicao
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try{
            response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
        }catch (IOException e){
            throw new RuntimeException();
        }catch (InterruptedException e){
            throw new RuntimeException();
        }
        String json = response.body();
        // retorno do metodo
        return json;


    }
}
