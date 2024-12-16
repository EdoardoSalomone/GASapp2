package it.edoardo.gasapp2.controller;

import it.edoardo.gasapp2.model.Catalog;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.CatalogRepository;
import it.edoardo.gasapp2.repository.ProductRepository;
import it.edoardo.gasapp2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    private final UserRepository userRepository;
    private final CatalogRepository catalogRepository;
    private final ProductRepository productRepository;

    @Autowired
    public AdminController(UserRepository userRepository, CatalogRepository catalogRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.catalogRepository = catalogRepository;
        this.productRepository = productRepository;
    }

    @PostMapping(value = "/catalog/create/{name}")
    public ResponseEntity<String> createCatalog(@RequestParam("name") String name){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByUsername(username).orElse(null);
        if(!Objects.isNull(authUser)){
            Catalog catalog = new Catalog();
            catalog.setName(name);
            catalog.setUser(authUser);
            catalogRepository.save(catalog);
            return ResponseEntity.ok("Catalogo aggiunto con successo");
        }
        return ResponseEntity.badRequest().body("Errore nella creazione del catalogo");
    }
}
