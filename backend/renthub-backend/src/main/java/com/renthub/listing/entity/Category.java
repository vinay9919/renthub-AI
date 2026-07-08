package com.renthub.listing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=500)
    private String description;

    @Column(nullable=false, unique=true)
    private String name;

    private String icon;

    private boolean active = true;

}