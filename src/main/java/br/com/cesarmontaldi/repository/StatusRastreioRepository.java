package br.com.cesarmontaldi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.StatusRastreio;

@Repository
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {

}
