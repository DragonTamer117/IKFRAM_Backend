package bikeShop.bike;

import bikeShop.bikeModel.BikeModel;
import bikeShop.bikeOrder.BikeOrder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Bike implements Serializable {
    @Id
    @SequenceGenerator(
            name = "bike_sequence",
            sequenceName = "bike_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "bike_sequence"
    )
    private Long bikeId;
    private String bikeName;
    @ManyToOne()
    @JoinColumn(name = "BM_ID")
    private BikeModel bikemodel;
    private float price;

    @ManyToOne()
    @JoinColumn(name = "BO_ID")
    private BikeOrder bikeOrder;

    private boolean bikeInStock;
    private boolean bikeIsSold;

    public Bike() {

    }

    public Bike(String bikeName, BikeModel bikemodel, float price, BikeOrder bikeOrder, boolean bikeInStock, boolean bikeIsSold) {
        this.bikeName = bikeName;
        this.bikemodel = bikemodel;
        this.price = price;
        this.bikeOrder = bikeOrder;
        this.bikeInStock = bikeInStock;
        this.bikeIsSold = bikeIsSold;
    }

    public Bike(Long bikeId, String bikeName, BikeModel bikemodel, float price, BikeOrder bikeOrder, boolean bikeInStock, boolean bikeIsSold) {
        this.bikeId = bikeId;
        this.bikeName = bikeName;
        this.bikemodel = bikemodel;
        this.price = price;
        this.bikeOrder = bikeOrder;
        this.bikeInStock = bikeInStock;
        this.bikeIsSold = bikeIsSold;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public BikeModel getBikemodel() {
        return bikemodel;
    }

    public void setBikemodel(BikeModel bikemodel) {
        this.bikemodel = bikemodel;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public BikeOrder getBikeOrder() {
        return bikeOrder;
    }

    public void setBikeOrder(BikeOrder bikeOrder) {
        this.bikeOrder = bikeOrder;
    }

    public boolean isBikeInStock() {
        return bikeInStock;
    }

    public void setBikeInStock(boolean bikeInStock) {
        this.bikeInStock = bikeInStock;
    }

    public boolean isBikeIsSold() {
        return bikeIsSold;
    }

    public void setBikeIsSold(boolean bikeIsSold) {
        this.bikeIsSold = bikeIsSold;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "bikeId=" + bikeId +
                ", bikeName='" + bikeName + '\'' +
                ", bikemodel=" + bikemodel +
                ", price=" + price +
                ", bikeOrder=" + bikeOrder +
                ", bikeInStock=" + bikeInStock +
                ", bikeIsSold=" + bikeIsSold +
                '}';
    }
}
