package it.edoardo.gasapp2.controller;

import it.edoardo.gasapp2.dto.user.RegistrationResponseDTO;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.UserRepository;
import it.edoardo.gasapp2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(userService.fromUserToResponseDTO(null));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User utenteRegistrato =userRepository.save(user);
        return ResponseEntity.ok(userService.fromUserToResponseDTO(utenteRegistrato));
    }
}
