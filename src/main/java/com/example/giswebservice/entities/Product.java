package com.example.giswebservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    private String name;

    private String description;

    private String price;

    private String img_url;

    private String page_url;

    private String category;

    private String shop_name;
}
