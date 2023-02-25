package br.com.angeloni.precos.server.data;

import lombok.Data;
import org.apache.commons.codec.binary.Hex;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "AUDITORIA_PRECOS")
@Data
public class AuditoriaEntity {

  @Id
  @Column(name = "ID", length = 16)
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private UUID id;

  @Column(name = "USUARIO", length = 200, nullable = false, updatable = false)
  private String usuario;

  @Column(name = "IP", length = 20, nullable = false, updatable = false)
  private String ip;

  @Column(name = "NOME", length = 200, nullable = false, updatable = false)
  private String nomeArquivo;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "CONTEUDO", nullable = false, updatable = false)
  private byte[] conteudoArquivo;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "RESULTADO")
  private String resultado;

  @Column(name = "SUCESSO")
  private Boolean sucesso;

  @Column(name = "DH_INICIAL", nullable = false, updatable = false)
  private LocalDateTime dataHoraInicio;

  @Column(name = "DH_FINAL")
  private LocalDateTime dataHoraFim;

  public String getStringId() {
    if (this.id == null) {
      return null;
    }
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(this.id.getMostSignificantBits());
    bb.putLong(this.id.getLeastSignificantBits());
    return Hex.encodeHexString(bb, false);
  }

  public static UUID fromStringId(String stringId) {
    if (stringId == null) {
      return null;
    }
    String uuidString = stringId.replaceAll(
        "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
        "$1-$2-$3-$4-$5");
    return UUID.fromString(uuidString);
  }

}
