package bikeShop.bikeOrder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class BikeOrder implements Serializable {
    @Id
    @SequenceGenerator(
            name = "bike_order_sequence",
            sequenceName = "bike_order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bike_order_sequence"
    )
    @Column(name = "ORDER_ID")
    private Long orderId;
    private BikeOrderStatus bikeOrderStatus = BikeOrderStatus.ORDER_CONFIRMED;
    // to prevent sending user info when sending order to the client
    // self coded shopUser lookup has to be implemented
    private Long shopUserId;

    public BikeOrder() {
    }

    public BikeOrder(BikeOrderStatus bikeOrderStatus, Long shopUserId) {
        this.bikeOrderStatus = bikeOrderStatus;
        this.shopUserId = shopUserId;
    }

    public BikeOrder(Long orderId, BikeOrderStatus bikeOrderStatus, Long shopUserId) {
        this.orderId = orderId;
        this.bikeOrderStatus = bikeOrderStatus;
        this.shopUserId = shopUserId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BikeOrderStatus getBikeOrderStatus() {
        return bikeOrderStatus;
    }

    public void setBikeOrderStatus(BikeOrderStatus bikeOrderStatus) {
        this.bikeOrderStatus = bikeOrderStatus;
    }

    public Long getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }
}
