package bikeShop.bikeModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class BikeModel implements Serializable {
    @Id
    @SequenceGenerator(
            name = "bikeModel_sequence",
            sequenceName = "bikeModel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bikeModel_sequence"
    )
    private Long bikeModelId;

    private String bikeModelName;
    private float priceOfTheDay;
    private int minStockCount;
    private String photoUrl;

    public BikeModel() {
    }

    public BikeModel(String bikeModelName, float priceOfTheDay, int minStockCount, String photoUrl) {
        this.bikeModelName = bikeModelName;
        this.priceOfTheDay = priceOfTheDay;
        this.minStockCount = minStockCount;
        this.photoUrl = photoUrl;
    }

    public BikeModel(Long bikeModelId, String bikeModelName, float priceOfTheDay, int minStockCount, String photoUrl) {
        this.bikeModelId = bikeModelId;
        this.bikeModelName = bikeModelName;
        this.priceOfTheDay = priceOfTheDay;
        this.minStockCount = minStockCount;
        this.photoUrl = photoUrl;
    }

    public Long getBikeModelId() {
        return bikeModelId;
    }

    public void setBikeModelId(Long bikeModelId) {
        this.bikeModelId = bikeModelId;
    }

    public String getBikeModelName() {
        return bikeModelName;
    }

    public void setBikeModelName(String bikeModelName) {
        this.bikeModelName = bikeModelName;
    }

    public float getPriceOfTheDay() {
        return priceOfTheDay;
    }

    public void setPriceOfTheDay(float priceOfTheDay) {
        this.priceOfTheDay = priceOfTheDay;
    }

    public int getMinStockCount() {
        return minStockCount;
    }

    public void setMinStockCount(int minStockCount) {
        this.minStockCount = minStockCount;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "BikeModel{" +
                "bikeModelId=" + bikeModelId +
                ", bikeModelName='" + bikeModelName + '\'' +
                ", priceOfTheDay=" + priceOfTheDay +
                ", minStockCount=" + minStockCount +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
