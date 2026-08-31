package com.example.foodservice.storeservice;

import com.example.foodservice.brandservice.dto.StoreDTO.StoreInfo;
import com.example.foodservice.common.exception.InvalidCredentialsException;
import com.example.foodservice.common.security.JwtService;
import com.example.foodservice.common.security.SubjectType;
import com.example.foodservice.storeservice.dto.AuthDTO.LoginCommand;
import com.example.foodservice.storeservice.dto.ProductDTO.ProductInfo;
import com.example.foodservice.storeservice.dto.ProductDTO.ProductOfferInfo;
import com.example.foodservice.storeservice.dto.StockDTO.AddStockCommand;
import com.example.foodservice.storeservice.dto.StockDTO.StockInfo;
import com.example.foodservice.storeservice.entity.Stock;
import com.example.foodservice.storeservice.exception.*;
import com.example.foodservice.storeservice.entity.Store;
import com.example.foodservice.storeservice.repository.StockRepository;
import com.example.foodservice.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StockRepository stockRepository;

    private final ProductCatalog productCatalog;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Transactional
    public StoreInfo register(Long brandId, String email, String rawPassword, String address) {

        if (storeRepository.existsByEmail(email)) {
            throw new StoreEmailExistsException(email);
        }

        Store store = new Store(brandId, email, address);

        store.setPasswordHash(passwordEncoder.encode(rawPassword));

        storeRepository.save(store);

        return new StoreInfo(store.getId(), store.getEmail(), store.getAddress());
    }

    @Transactional(readOnly = true)
    public String login(LoginCommand command) {
        Store store = storeRepository.findByEmail(command.email()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(command.rawPassword(), store.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return jwtService.generateAccessToken(store.getId(), store.getBrandId(), SubjectType.STORE);
    }

    @Transactional
    public StockInfo addStock(AddStockCommand command) {
        if (stockRepository.existsByStoreIdAndProductId(command.storeId(), command.productId())) {
            throw new StockAlreadyExistsException(command.storeId(), command.productId());
        }

        //TODO: добавить проверку productId, не критично

        Store store = storeRepository.getReferenceById(command.storeId());

        Stock stock = command.toStock(store);

        try {
            stockRepository.saveAndFlush(stock);
        } catch (DataIntegrityViolationException e) {
            throw new StockAlreadyExistsException(command.storeId(), command.productId());
        }

        return StockInfo.from(stock);
    }

    @Transactional(readOnly = true)
    public List<StoreInfo> getStoresByBrandId(Long brandId) {
        List<Store> stores = storeRepository.findByBrandId(brandId);
        return stores.stream().map(store -> new StoreInfo(store.getId(), store.getEmail(), store.getAddress())).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductOfferInfo> getBrandProductsOffers(Long brandId) {

        List<Store> stores = storeRepository.findByBrandId(brandId);
        if (stores.isEmpty()) {throw new StoreNotFoundByBrandException(brandId);}

        //TODO: в будущем будет вычислятся по координатам
        Store store = stores.getFirst();

        //TODO: также нужно будет подумать над тем как отдавать это, какие фильтры применять, как это разнести по категориям
        //скорее всего вырастит в отдельный метод, как и метод выше
        List<Stock> stocks = store.getStock();

        // проверка вроде на уникальность не нужна, она обеспечивается unique constraint на сущности по storeId и productId
        List<Long> productIds = stocks.stream().map(Stock::getProductId).toList();


        //TODO: в будущем при разьезде нужно также обрабатывать, ведь может приехать ошибка, пока что она обрабатывается другим сервисом
        List<ProductInfo> productsInfo = productCatalog.getProductsByIds(productIds);

        Map<Long, ProductInfo> productsInfoMap = productsInfo.stream()
                .collect(Collectors.toMap(ProductInfo::id, Function.identity()));

        return stocks.stream().map(stock ->
            ProductOfferInfo.from(productsInfoMap.get(stock.getProductId()), stock)
        ).toList();
    }


//    @Transactional(readOnly = true)
//    public List<ProductInfo> getProductsByIds(List<Long> menuItemsIds) {
//
//        List<Product> products = productRepository.findAllByIdWithStore(menuItemsIds);
//
//        if(menuItemsIds.size() != products.size()) {
//            throw new SomeMenuItemsMissingException();
//        }
//
//        return products.stream()
//                .map(ProductInfo::from)
//                .toList();
//    }

//    @Transactional
//    public void decreaseProductQuantity(List<OrderLine> lines) {
//       List<OrderLine> sortedLines = lines.stream() // одинаковый порядок сортировки (от дедлоков)
//               .sorted(Comparator.comparing(OrderLine::menuItemId))
//               .toList();
//
//        for(OrderLine line : sortedLines) {
//            int rowsAffected = productRepository.decreaseQuantity(line.menuItemId(), line.quantity());
//            if(rowsAffected == 0) {
//                throw new NotEnoughMenuItemQuantityException(line.menuItemId(), line.quantity());
//            }
//        }
//    }

}
