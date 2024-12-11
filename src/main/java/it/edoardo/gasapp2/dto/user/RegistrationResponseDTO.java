package it.edoardo.gasapp2.dto.user;

import it.edoardo.gasapp2.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {
    private String message;
    private UserRegistrationResponseDTO userResponse;
}
