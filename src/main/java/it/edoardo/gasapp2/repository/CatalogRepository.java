package it.edoardo.gasapp2.repository;

import it.edoardo.gasapp2.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog,Integer> {

    public Optional<Catalog> findByUser_IdAndName(Integer userId, String name);

    public List<Catalog> findAllCatalogByUser_Id(Integer userId);
}
