package bikeShop.bikeModel;

import bikeShop.ShopUserValidator;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BikeModelService {

    private final BikeModelRepository bikeModelRepository;
    private final ShopUserValidator shopUserValidator;


    @Autowired
    public BikeModelService(BikeModelRepository bikeModelRepository, ShopUserValidator shopUserValidator) {
        this.bikeModelRepository = bikeModelRepository;
        this.shopUserValidator =  shopUserValidator;
    }

    public List<BikeModel> getAllBikeModels(ShopUserAuth shopUserAuth) {
        if (!this.shopUserValidator.shopAllUsersIsOk(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        return bikeModelRepository.findAll();
    }

    public Optional<BikeModel> getBikeModelById(ShopUserAuth shopUserAuth,Long bikeModelId) {
        if (!this.shopUserValidator.shopCustomerIsOK(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        Optional <BikeModel> bikeModelById = bikeModelRepository.findBikeModelByBikeModelId(bikeModelId);
        if (bikeModelById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"BikeModel with the id: " + bikeModelId + " does not exist!");
        }
        return bikeModelById;
    }
}
