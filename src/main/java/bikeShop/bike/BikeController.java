package bikeShop.bike;

import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/bike")
public class BikeController {

    private final BikeService bikeService;

    @Autowired
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public List<Bike> getAllBikes(@RequestBody ShopUserAuth shopUserAuth){
        return bikeService.getAllBikes(shopUserAuth);
    }

    @PostMapping(path = "/bikeOrder/{bikeOrderId}")
    public List<Bike> getBikeByBikeOrderId(@RequestBody ShopUserAuth shopUserAuth,@PathVariable("bikeOrderId") Long bikeOrderId){
        List<Bike> bikes = bikeService.getAllBikesByBikeOrderId(shopUserAuth,bikeOrderId);
        return bikes;
    }
    @PostMapping(path = "/add/{bikeOrderId}" )
    public Long registerNewBike(@RequestBody ShopUserAuth shopUserAuth,
                                @PathVariable("bikeOrderId")  Long bikeOrderId,
                                @RequestParam("bikeModelId")  Long bikeModelId){
        return bikeService.addNewBike(shopUserAuth,bikeOrderId,bikeModelId);
    }

}
