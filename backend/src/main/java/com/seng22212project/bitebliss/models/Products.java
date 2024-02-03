package com.seng22212project.bitebliss.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Products {

    @Id
    @Column(name="product_id")
    @GeneratedValue
    private int id;

    @Column(name="product_name")
    private String name;

    @Column(name="product_price")
    private String price;

    @Column(name="product_description")
    private String description;

    @Column(name="product_image")
    private Blob image;

    @Column(name="category_id")
    private int category_id;

}
