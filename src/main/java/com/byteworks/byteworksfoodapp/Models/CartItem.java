package com.byteworks.byteworksfoodapp.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem extends BaseModel{


    private Long foodId;

    @Column
    private Long quantity;

    @Column
    private Long price;

    @Column
    private Date deliveryDate;

    @Column
    private String status;

    @Column(columnDefinition = "TEXT")
    private String otherInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "CartItem{" +
                "foodId=" + foodId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", deliveryDate=" + deliveryDate +
                ", status='" + status + '\'' +
                ", otherInfo='" + otherInfo + '\'' +
                ", user=" + user.getEmail() + user.getId() +
                '}';
    }
}