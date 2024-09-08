package com.forj.order.domain.model;

import com.forj.order.domain.enums.OrderStatusEnum;
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
    private OrderStatusEnum status;

    private Boolean isdelete;

    // 주문 생성
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
                .status(OrderStatusEnum.PROGRESS)
                .isdelete(false)
                .build();
    }

    // 주문 내용 수정
    public void update(
            UUID receivingCompanyId,
            UUID productId,
            Integer quantity,
            UUID deliveryId
    ) {
        this.receivingCompanyId = receivingCompanyId;
        this.productId = productId;
        this.quantity = quantity;
        this.deliveryId = deliveryId;
    }

    // 주문 삭제를 위한 setter 생성
    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

    // 주문 상태 변경을 위한 setter 생성
    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    // 주문 취소를 위한 메서드 생성
    public void cancelOrder(){ this.status = OrderStatusEnum.CANCELED; }
}
