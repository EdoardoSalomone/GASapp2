package it.edoardo.gasapp2.service;

import it.edoardo.gasapp2.model.Catalog;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.CatalogRepository;
import it.edoardo.gasapp2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository, UserRepository userRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Boolean isUserCatalogExisting(String catalogName, User user){
        Catalog userCatalog = catalogRepository.findByUser_IdAndName(user.getId(),catalogName).orElse(null);
        return userCatalog != null;
    }
}
