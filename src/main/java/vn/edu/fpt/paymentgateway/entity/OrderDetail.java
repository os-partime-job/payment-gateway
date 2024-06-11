package vn.edu.fpt.paymentgateway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDER_DETAIL")
@Data
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "jewelry_id")
    private Long jewelryId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "quantity_number")
    private Integer quantityNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "unique_order_id")
    private String uniqueOrderId;
}
