package br.com.angeloni.precos.server.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditoriaRepository extends JpaRepository<AuditoriaEntity, UUID> {

  @Query("SELECT e FROM AuditoriaEntity e WHERE e.usuario = :usuario")
  Page<AuditoriaEntity> findHistorico(@Param("usuario") String usuario, Pageable pageable);

}
