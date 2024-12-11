package it.edoardo.gasapp2.repository;

import it.edoardo.gasapp2.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog,Integer> {
}
