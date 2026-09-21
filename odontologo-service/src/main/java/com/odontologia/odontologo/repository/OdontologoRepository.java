package com.odontologia.odontologo.repository;

import com.odontologia.odontologo.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    Optional<Odontologo> findByMatricula(String matricula);
    Boolean existsByMatricula(String matricula);
}
