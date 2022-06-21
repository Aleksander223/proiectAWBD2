package com.proiect2.order.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "order")
public class Order extends RepresentationModel<Order>{
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

@Column(name="total")
@NonNull
private Float total;

    public Order(float total) {
        this.total = total;
    }
}
