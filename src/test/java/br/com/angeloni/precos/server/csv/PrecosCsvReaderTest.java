package br.com.angeloni.precos.server.csv;

import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

public class PrecosCsvReaderTest {

  private PrecosCsvReader precosCsvReader = new PrecosCsvReader();

  private InputStream getCsv(String fileName) throws FileNotFoundException {
    return new FileInputStream(new File("src/test/resources/mock/" + fileName));
  }

  private void printLista(List<? extends Object> lista) {
    for (Object o : lista) {
      System.err.println(o);
    }
  }

  @Test
  public void processaPrecoManualEletro() throws IOException {
    String csv = "Preco_manual_Eletro.csv";
    Layout.Template template = precosCsvReader.detectaLayout(getCsv(csv));
    assertEquals(Layout.Template.Eletro, template);
    List<PrecoManualBean> lista = precosCsvReader.processa(getCsv(csv), template);
    assertEquals(2, lista.size());
    printLista(lista);
  }

  @Test
  public void processaPrecoManualSuper() throws IOException {
    String csv = "Preco_manual_Super.csv";
    Layout.Template template = precosCsvReader.detectaLayout(getCsv(csv));
    assertEquals(Layout.Template.Super, template);
    List<PrecoManualBean> lista = precosCsvReader.processa(getCsv(csv), template);
    assertEquals(2, lista.size());
    printLista(lista);
  }

  @Test
  public void processaPrecoManualDivvino() throws IOException {
    String csv = "Preco_manual_Divvino.csv";
    Layout.Template template = precosCsvReader.detectaLayout(getCsv(csv));
    assertEquals(Layout.Template.Divvino, template);
    List<PrecoManualBean> lista = precosCsvReader.processa(getCsv(csv), template);
    assertEquals(2, lista.size());
    printLista(lista);
  }

  @Test
  public void processaPromocaoManual() throws IOException {
    String csv = "Promocao_manual.csv";
    Layout.Template template = precosCsvReader.detectaLayout(getCsv(csv));
    assertEquals(Layout.Template.Promocao, template);
    List<PromocaoManualBean> lista = precosCsvReader.processa(getCsv(csv), template);
    assertEquals(5, lista.size());
    printLista(lista);
  }

  @Test
  public void processaPromocaoManualPN() throws IOException {
    String csv = "Promocao_PN_manual.csv";
    Layout.Template template = precosCsvReader.detectaLayout(getCsv(csv));
    assertEquals(Layout.Template.PromocaoN, template);
    List<PromocaoManualBean> lista = precosCsvReader.processa(getCsv(csv), template);
    assertEquals(4, lista.size());
    printLista(lista);
  }
}