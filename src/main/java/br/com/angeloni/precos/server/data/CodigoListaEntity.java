package br.com.angeloni.precos.server.data;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CODIGO_LISTA_PRECOS",
    uniqueConstraints = {
      @UniqueConstraint(name = "codigo_lista_idx1", columnNames = {"TIPO", "NOME", "LOJA"}),
      @UniqueConstraint(name = "codigo_lista_idx2", columnNames = {"CODIGO"})
    })
@Data
public class CodigoListaEntity {

  @Id
  @Column(name = "ID", length = 16)
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private UUID id;

  @Column(name = "TIPO", length = 100, nullable = false, updatable = false)
  private String tipo;

  @Column(name = "NOME", length = 100, nullable = false, updatable = false)
  private String nome;

  @Column(name = "LOJA", nullable = false, updatable = false)
  private Long loja;

  @Column(name = "CODIGO", length = 100, nullable = false, updatable = false)
  private String codigo;

}
