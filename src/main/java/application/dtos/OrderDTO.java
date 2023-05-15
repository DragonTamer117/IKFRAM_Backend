package application.dtos;

import application.enums.OrderStatus;
import application.models.users.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.JoinColumn;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {
    @JoinColumn(name = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private User user;
    private OrderStatus orderStatus;
    private List<UUID> products;
}
