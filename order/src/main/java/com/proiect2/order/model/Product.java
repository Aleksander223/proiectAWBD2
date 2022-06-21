package com.proiect2.order.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product extends RepresentationModel<Product>{
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

@Column(name="name")
@Size(max=20, message = "max 20 ch.")
private String name;

@Column(name="category")
@Size(max=30, message = "max 30 ch.")
private String category;

@Column(name="brand")
@Size(max=30, message = "max 30 ch.")
private String brand;

@Column(name="description")
@Size(max=300, message = "max 300 ch.")
private String description;

@Column(name="price")
private int price;
}
