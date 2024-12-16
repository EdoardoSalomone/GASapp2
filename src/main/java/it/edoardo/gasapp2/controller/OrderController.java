package it.edoardo.gasapp2.controller;

import it.edoardo.gasapp2.model.Order;
import it.edoardo.gasapp2.model.Product;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.OrderRepository;
import it.edoardo.gasapp2.repository.ProductRepository;
import it.edoardo.gasapp2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/utenti/ordini")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        // Ottieni l'utente autenticato
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Trova l'utente
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Ritorna gli ordini dell'utente
        return orderRepository.findByUser(user).orElse(new ArrayList<>());
    }

    @PostMapping("/{productId}")
    public Order createOrder(@PathVariable Integer productId, @RequestBody Order order) {
        // Ottieni l'utente autenticato
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Trova l'utente
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Trova il prodotto
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        // Associa l'ordine all'utente e al prodotto
        order.setUser(user);
        order.setProduct(product);

        // Salva l'ordine
        return orderRepository.save(order);
    }
}
