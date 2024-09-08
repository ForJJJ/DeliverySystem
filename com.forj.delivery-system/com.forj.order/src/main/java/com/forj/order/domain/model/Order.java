package com.forj.order.domain.model;

import com.forj.order.domain.enums.OrderEnum;
import com.forj.order.presentation.request.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_order")
@Builder
public class Order {

    @Id
    @UuidGenerator
    private UUID orderId;

    private UUID requestCompanyId;
    private UUID receivingCompanyId;
    private UUID productId;
    private Integer quantity;
    private UUID deliveryId;

    @Enumerated(EnumType.STRING)
    private OrderEnum status;

    public static Order create(
            UUID requestCompanyId,
            UUID receivingCompanyId,
            UUID productId,
            Integer quantity,
            UUID deliveryId
    ){
        return Order.builder()
                .requestCompanyId(requestCompanyId)
                .receivingCompanyId(receivingCompanyId)
                .productId(productId)
                .quantity(quantity)
                .deliveryId(deliveryId)
                .status(OrderEnum.PROGRESS)
                .build();
    }
}
