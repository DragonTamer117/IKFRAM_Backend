package bikeShop.bikeModel;

import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/bikeModel")
public class BikeModelController {
        private BikeModelService bikeModelService;

        @Autowired
        public BikeModelController(BikeModelService bikeModelService){this.bikeModelService = bikeModelService;}

        @PostMapping
        public List<BikeModel> getAllBikeModels(@RequestBody ShopUserAuth shopUserAuth) {
                return bikeModelService.getAllBikeModels(shopUserAuth);}

        @GetMapping(path = "/{bikeModelId}")
        public Optional<BikeModel> getBikeById(@RequestBody ShopUserAuth shopUserAuth,@PathVariable("bikeModelId") Long bikeModelId){
                Optional<BikeModel> bikeModelById = bikeModelService.getBikeModelById(shopUserAuth,bikeModelId);
                return bikeModelById;
        }
        // for the time being no postmapping for adding BikeModel entities
        // Bikemodels are added by calling the service method addBikmodel in dataconfig
}
