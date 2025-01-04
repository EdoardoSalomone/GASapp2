package it.edoardo.gasapp2.controller;

import it.edoardo.gasapp2.dto.catalog.CatalogResponseDTO;
import it.edoardo.gasapp2.model.Catalog;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.CatalogRepository;
import it.edoardo.gasapp2.repository.UserRepository;
import it.edoardo.gasapp2.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    private final UserRepository userRepository;
    private final CatalogRepository catalogRepository;
    private final CatalogService catalogService;

    @Autowired
    public AdminController(UserRepository userRepository, CatalogRepository catalogRepository, CatalogService catalogService) {
        this.userRepository = userRepository;
        this.catalogRepository = catalogRepository;
        this.catalogService = catalogService;
    }

    @GetMapping(value = "/catalog/create/{name}")
    public ResponseEntity<Map<String,String>> createCatalog(@PathVariable("name") String name){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByUsername(username).orElse(null);
        if (Objects.isNull(authUser)) {
            return ResponseEntity.status(403).body(Map.of("message", "Errore nel token di autenticazione"));
        }
        if(catalogService.isUserCatalogExisting(name, authUser)){
           return ResponseEntity.status(409).body(Map.of("message", "Catalogo già esistente per questo utente"));
        }
            Catalog catalog = new Catalog();
            catalog.setName(name);
            catalog.setUser(authUser);
            catalogRepository.save(catalog);
            return ResponseEntity.status(200).body(Map.of("message","catalogo creato con successo"));
    }

    @GetMapping(value = "/catalog/get/userCatalogs")
    public ResponseEntity<List<CatalogResponseDTO>> getUserCatalogs(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByUsername(username).orElse(null);
        if (!Objects.isNull(authUser)) {
            return ResponseEntity.ok(catalogService.getUserCatalog(authUser));
        }
        return ResponseEntity.status(403).body(Collections.emptyList());
    }
}
