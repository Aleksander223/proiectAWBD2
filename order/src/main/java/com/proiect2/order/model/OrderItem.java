package com.proiect2.order.model;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem extends RepresentationModel<OrderItem>{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="product")
    @NonNull
    private Long product;

    @Column(name="quantity")
    @Min(value = 1, message = "Must have quantity at least 1")
    private Integer quantity;
}
