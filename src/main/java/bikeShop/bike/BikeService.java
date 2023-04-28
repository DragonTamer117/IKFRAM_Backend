package bikeShop.bike;

import bikeShop.ShopUserValidator;
import bikeShop.bikeModel.BikeModel;
import bikeShop.bikeModel.BikeModelRepository;
import bikeShop.bikeOrder.BikeOrder;
import bikeShop.bikeOrder.BikeOrderRepository;
import bikeShop.shopUser.ShopUserService;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BikeService {

    private final BikeRepository bikeRepository;
    private final BikeOrderRepository bikeOrderRepository;
    private final BikeModelRepository bikeModelRepository;
    private final ShopUserService shopUserService;
    private final ShopUserValidator shopUserValidator;

    @Autowired
    public BikeService(BikeRepository bikeRepository,
                       BikeOrderRepository bikeOrderRepository,
                       BikeModelRepository bikeModelRepository,
                       ShopUserService shopUserService,
                       ShopUserValidator shopUserValidator) {
        this.bikeRepository = bikeRepository;
        this.bikeOrderRepository = bikeOrderRepository;
        this.bikeModelRepository = bikeModelRepository;
        this.shopUserService = shopUserService;
        this.shopUserValidator = shopUserValidator;
    }

    public List<Bike> getAllBikes(ShopUserAuth shopUserAuth){
        if (!this.shopUserValidator.shopAdminIsOk(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        return bikeRepository.findAll();
    }

    public List<Bike> getAllBikesByBikeOrderId(ShopUserAuth shopUserAuth, Long bikeOrderId) {
        if (!this.shopUserValidator.shopCustomerIsOK(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        Optional<BikeOrder> bikeOrderById = bikeOrderRepository.findBikeOrderByOrderId(bikeOrderId);
        if (bikeOrderById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"BikeOrder with id: " + bikeOrderId +  " does not exist!");
        }
        return bikeRepository.findAllByBikeOrder(bikeOrderById.get());}


    public Long addNewBike(ShopUserAuth shopUserAuth,Long bikeModelId, Long bikeOrderId) {
        if (!this.shopUserValidator.shopCustomerIsOK(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        Optional <BikeOrder> bikeOrderByOrderId = bikeOrderRepository.findBikeOrderByOrderId(bikeOrderId);
        if (bikeOrderByOrderId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"BikeOrder with id: " + bikeOrderId +  " does not exist!");
        }
        Optional <BikeModel> bikeModelById = bikeModelRepository.findBikeModelByBikeModelId(bikeModelId);
        if (bikeModelById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"BikeModel with id: " + bikeModelId +  " does not exist!");
        }
        Bike bike = new Bike(bikeModelById.get().getBikeModelName(),
                            bikeModelById.get(),
                            bikeModelById.get().getPriceOfTheDay(),
                            bikeOrderByOrderId.get(),false,true);
        return bikeRepository.save(bike).getBikeId();
    }
}
