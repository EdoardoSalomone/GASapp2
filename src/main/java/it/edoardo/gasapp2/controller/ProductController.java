package it.edoardo.gasapp2.controller;

import it.edoardo.gasapp2.model.Catalog;
import it.edoardo.gasapp2.model.Product;
import it.edoardo.gasapp2.repository.CatalogRepository;
import it.edoardo.gasapp2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/prodotti")
public class ProductController {

    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, CatalogRepository catalogRepository) {
        this.productRepository = productRepository;
        this.catalogRepository = catalogRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/{catalogId}")
    public Product createProduct(@PathVariable Integer catalogId, @RequestBody Product product) {
        // Ottieni l'utente autenticato
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Trova il catalogo associato al venditore
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new RuntimeException("Catalogo non trovato"));

        if (!catalog.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Non sei autorizzato a modificare questo catalogo");
        }

        // Associa il prodotto al catalogo
        product.setCatalog(catalog);

        // Salva il prodotto
        return productRepository.save(product);
    }
}
