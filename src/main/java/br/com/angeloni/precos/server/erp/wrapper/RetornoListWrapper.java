package br.com.angeloni.precos.server.erp.wrapper;

import br.com.angeloni.precos.server.erp.model.RetornoControlePreco;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RetornoListWrapper {

    @JsonProperty("Retorno")
    private List<RetornoControlePreco> retorno;

}
