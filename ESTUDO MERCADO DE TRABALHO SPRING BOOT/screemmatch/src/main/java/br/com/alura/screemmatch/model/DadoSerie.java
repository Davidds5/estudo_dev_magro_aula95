package br.com.alura.screemmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DadoSerie(@JsonAlias({"Title", "Titulo"}) String titulo,
                        @JsonAlias("totalSeasons") Integer totalTemporadas,
                        @JsonAlias("indbRating") String avaliacao,
                        @JsonProperty("indbVotes") String votos
                        ) {


}
