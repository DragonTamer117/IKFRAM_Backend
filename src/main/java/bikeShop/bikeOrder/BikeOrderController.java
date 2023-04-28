package bikeShop.bikeOrder;

import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/bikeOrder")
public class BikeOrderController {

    private final BikeOrderService bikeOrderService;

    @Autowired
    public BikeOrderController(BikeOrderService bikeOrderService){this.bikeOrderService = bikeOrderService;}

    @PostMapping
    public List<BikeOrder> getAllBikeOrders(@RequestBody ShopUserAuth shopUserAuth) {
        List<BikeOrder> bikeOrders = bikeOrderService.getAllBikeOrders(shopUserAuth);
        return bikeOrders;
    }

    @GetMapping(path = "/{bikeOrderId}")
    public Optional<BikeOrder> getBikeOrderById(@RequestBody ShopUserAuth shopUserAuth,
                                                @PathVariable("bikeOrderId") Long bikeOrderId){
        return bikeOrderService.getBikeOrderById(shopUserAuth,bikeOrderId);
    }

    @PostMapping(path = "/{shopUserId}")
    public Long registerNewBikeOrder(@RequestBody BikeOrderSendRequest bikeOrderSendRequest,
                                     @PathVariable("shopUserId") Long shopUserId){
        Long newBikeOrderId = bikeOrderService.addNewBikeOrder(bikeOrderSendRequest,shopUserId);
        return newBikeOrderId;
    }

    @PutMapping(path = "/update/{bikeOrderId}")
    public void updateBikeOrder(@RequestBody ShopUserAuth shopUserAuth,
                                @PathVariable("bikeOrderId") Long bikeOrderId,
                                @PathVariable("newOrderStatus") BikeOrderStatus newBikeOrderStatus){
        bikeOrderService.updateBikeOrder(shopUserAuth,bikeOrderId,newBikeOrderStatus);
    }


    @PostMapping(path = "/delete/{bikeOrderId}")
    public void deleteBikeOrder(@RequestBody ShopUserAuth shopUserAuth,
                                @PathVariable("bikeOrderId") Long bikeOrderId){
        bikeOrderService.deleteBikeOrder(shopUserAuth,bikeOrderId);
    }


}