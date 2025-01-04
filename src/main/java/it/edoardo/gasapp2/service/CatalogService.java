package it.edoardo.gasapp2.service;

import it.edoardo.gasapp2.dto.catalog.CatalogResponseDTO;
import it.edoardo.gasapp2.mapper.catalog.CatalogMapper;
import it.edoardo.gasapp2.model.Catalog;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.CatalogRepository;
import it.edoardo.gasapp2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository, UserRepository userRepository, CatalogMapper catalogMapper) {
        this.catalogRepository = catalogRepository;
        this.catalogMapper = catalogMapper;
    }

    public Boolean isUserCatalogExisting(String catalogName, User user){
        Catalog userCatalog = catalogRepository.findByUser_IdAndName(user.getId(),catalogName).orElse(null);
        return userCatalog != null;
    }

    public List<CatalogResponseDTO> getUserCatalog(User user){
        List<Catalog> catalogs = catalogRepository.findAllCatalogByUser_Id(user.getId());
        return catalogs.stream()
                .map(catalogMapper :: fromCatalogToCatalogResponseDTO)
                .toList();
    }
}
