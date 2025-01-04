package it.edoardo.gasapp2.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResponseDTO implements Serializable {
    private Integer id;
    private String name;
}
