package it.edoardo.gasapp2.controller;

import it.edoardo.gasapp2.dto.user.AuthResponse;
import it.edoardo.gasapp2.dto.user.RegistrationResponseDTO;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.UserRepository;
import it.edoardo.gasapp2.service.UserService;
import it.edoardo.gasapp2.utils.JwUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwUtils jwtUtils;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService, JwUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(userService.fromUserToResponseDTO(null));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        return ResponseEntity.ok(userService.fromUserToResponseDTO(registeredUser));
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
            String role = foundUser.get().getRole().name();
            String token = jwtUtils.generateToken(foundUser.get().getUsername(), List.of(role));
            return ResponseEntity.ok(new AuthResponse(token, "Login avvenuto con successo"));
        }
        return ResponseEntity.status(401).body(new AuthResponse(null, "Credenziali errate"));
    }
}