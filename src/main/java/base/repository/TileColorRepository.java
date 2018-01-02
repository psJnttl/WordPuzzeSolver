package base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import base.domain.TileColor;

public interface TileColorRepository extends JpaRepository<TileColor, Long> {

}
