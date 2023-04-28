package bikeShop.bikeOrder;

import bikeShop.ShopUserValidator;
import bikeShop.bike.Bike;
import bikeShop.bike.BikeRepository;
import bikeShop.bikeModel.BikeModel;
import bikeShop.bikeModel.BikeModelService;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import bikeShop.shopUser.ShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class BikeOrderService {

    private final BikeOrderRepository bikeOrderRepository;
    private final BikeModelService bikeModelService;
    private final BikeRepository bikeRepository;
    private final ShopUserValidator shopUserValidator;
    private final ShopUserService shopUserService;

    @Autowired
    public BikeOrderService(BikeOrderRepository bikeOrderRepository,
                            BikeRepository bikeRepository,
                            BikeModelService bikeModelService,
                            ShopUserValidator shopUserValidator,
                            ShopUserService shopUserService){
        this.bikeOrderRepository = bikeOrderRepository;
        this.bikeModelService = bikeModelService;
        this.bikeRepository = bikeRepository;
        this.shopUserValidator = shopUserValidator;
        this.shopUserService = shopUserService;
    }

    public Optional<BikeOrder> getBikeOrderById(ShopUserAuth shopUserAuth,Long bikeOrderId) {
        if (!this.shopUserValidator.shopAdminIsOk(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        return bikeOrderRepository.findBikeOrderByOrderId(bikeOrderId);
    }

    public List<BikeOrder> getAllBikeOrders(ShopUserAuth shopUserAuth) {
        if (!this.shopUserValidator.shopAdminIsOk(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        List<BikeOrder> bikeOrders = bikeOrderRepository.findAll();
        if (bikeOrders.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"BikeOrders do not exist!");
        }
        return bikeOrders;
    }

    public Long addNewBikeOrder(BikeOrderSendRequest bikeOrderSendRequest, Long shopUserId) {
        if (!this.shopUserValidator.shopCustomerIsOK(bikeOrderSendRequest.shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");

        this.shopUserService.shopUserByIdExists(shopUserId);

        BikeOrder newBikeOrder = new BikeOrder(BikeOrderStatus.ORDER_CONFIRMED, shopUserId);
        newBikeOrder = bikeOrderRepository.save(newBikeOrder);
        Long bikeOrderId = newBikeOrder.getOrderId();
        for (Long bikeModelId : bikeOrderSendRequest.bikeModelIds) {
            Optional<BikeModel> bikeModelOptional = this.bikeModelService.getBikeModelById(bikeOrderSendRequest.shopUserAuth,bikeModelId);
            if (bikeModelOptional.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"bikeModel with id: " + bikeModelId +  " does not exist!");
            }
            Bike bike = new Bike(
                    bikeModelOptional.get().getBikeModelName(),
                    bikeModelOptional.get(),
                    bikeModelOptional.get().getPriceOfTheDay(),
                    newBikeOrder,
                    false,
                    true);
            this.bikeRepository.save(bike);
        }
        return bikeOrderId;
    }

    @Transactional
    public void updateBikeOrder(ShopUserAuth shopUserAuth,Long bikeOrderId,BikeOrderStatus newBikeOrderStatus) {
        if (!this.shopUserValidator.shopAdminIsOk(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        Optional<BikeOrder> bikeOrderById = bikeOrderRepository.findBikeOrderByOrderId(bikeOrderId);
        if (bikeOrderById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BikeOrder with id: " + bikeOrderId + " does not exist!");
        }
        bikeOrderById.get().setBikeOrderStatus(newBikeOrderStatus);
    }

    public void deleteBikeOrder(ShopUserAuth shopUserAuth, Long bikeOrderId) {
        if (!this.shopUserValidator.shopAdminIsOk(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        Optional<BikeOrder> bikeOrderById = bikeOrderRepository.findBikeOrderByOrderId(bikeOrderId);
        if (bikeOrderById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BikeOrder with id: " + bikeOrderId + " does not exist!");
        }
        bikeOrderRepository.delete(bikeOrderById.get());
    }
}
