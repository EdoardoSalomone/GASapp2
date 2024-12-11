package it.edoardo.gasapp2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "catalog_id",nullable = false)
    private Catalog catalog;

    public enum Unit {
        KG,
        PZ
    }
}
