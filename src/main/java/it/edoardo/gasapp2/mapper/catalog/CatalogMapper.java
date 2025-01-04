package it.edoardo.gasapp2.mapper.catalog;

import it.edoardo.gasapp2.dto.catalog.CatalogResponseDTO;
import it.edoardo.gasapp2.model.Catalog;
import org.springframework.stereotype.Component;

@Component
public class CatalogMapper {

    public CatalogResponseDTO fromCatalogToCatalogResponseDTO(Catalog catalog) {
        CatalogResponseDTO catalogResponseDTO = new CatalogResponseDTO();
        catalogResponseDTO.setId(catalog.getId());
        catalogResponseDTO.setName(catalog.getName());
        return catalogResponseDTO;
    }
}
