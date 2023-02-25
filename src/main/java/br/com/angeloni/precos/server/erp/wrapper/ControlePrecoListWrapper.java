package br.com.angeloni.precos.server.erp.wrapper;

import br.com.angeloni.precos.server.erp.model.ControlePreco;
import lombok.Data;

import java.util.List;

@Data
public class ControlePrecoListWrapper {

    private List<ControlePreco> ttPrecificacao;

}
