package it.edoardo.gasapp2.service;

import it.edoardo.gasapp2.dto.user.RegistrationResponseDTO;
import it.edoardo.gasapp2.dto.user.UserRegistrationResponseDTO;
import it.edoardo.gasapp2.model.User;
import it.edoardo.gasapp2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public RegistrationResponseDTO fromUserToResponseDTO(User user){
        if(!Objects.isNull(user)){
            RegistrationResponseDTO response = new RegistrationResponseDTO();
            response.setUserResponse(new UserRegistrationResponseDTO(user.getUsername(),user.getRole().toString()));
            response.setMessage("Utente registrato con successo");
            return response;
        } else {
            return new RegistrationResponseDTO("Utente già esistente",new UserRegistrationResponseDTO());
        }
    }
}
