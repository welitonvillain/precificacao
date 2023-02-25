package br.com.angeloni.precos.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodigoListaRepository extends JpaRepository<CodigoListaEntity, UUID> {

  @Query("SELECT e FROM CodigoListaEntity e WHERE e.tipo = :tipo AND e.nome = :nome AND e.loja = :loja")
  Optional<CodigoListaEntity> findLista(@Param("tipo") String tipo, @Param("nome") String name, @Param("loja") Long loja);

  @Query("SELECT e FROM CodigoListaEntity e WHERE e.loja IN (:lojas)")
  List<CodigoListaEntity> findListasByLojas(@Param("lojas") List<Long> lojas);

}
