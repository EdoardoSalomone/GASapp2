package it.edoardo.gasapp2.repository;

import it.edoardo.gasapp2.model.Order;
import it.edoardo.gasapp2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<List<Order>> findByUser(User user);
}
