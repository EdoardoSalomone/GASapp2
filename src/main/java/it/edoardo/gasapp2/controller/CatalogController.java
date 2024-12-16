package it.edoardo.gasapp2.controller;

import it.edoardo.gasapp2.model.Catalog;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.CatalogRepository;
import it.edoardo.gasapp2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cataloghi")
public class CatalogController {

    private final CatalogRepository catalogRepository;
    private final UserRepository userRepository;

    @Autowired
    public CatalogController(CatalogRepository catalogRepository, UserRepository userRepository) {
        this.catalogRepository = catalogRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    @PostMapping
    public Catalog createCatalog(@RequestBody Catalog catalog) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User venditore = userRepository.findByUsername(username).orElse(null); //TODO  Carica il venditore corrente dal DB
        catalog.setUser(venditore);
        return catalogRepository.save(catalog);
    }
}
