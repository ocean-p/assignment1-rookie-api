package com.daiduong.demo.service;

import java.util.List;
import java.util.Optional;

import com.daiduong.demo.dto.RatingDTO;
import com.daiduong.demo.entity.AccountEntity;
import com.daiduong.demo.entity.ProductEntity;
import com.daiduong.demo.entity.RatingEntity;
import com.daiduong.demo.exception.ApiRequestException;
import com.daiduong.demo.exception.ErrorCode;
import com.daiduong.demo.repository.AccountRepository;
import com.daiduong.demo.repository.ProductRepository;
import com.daiduong.demo.repository.RatingRepository;
import com.daiduong.demo.service.interfaces.IRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ErrorCode errorCode;

    @Override
    public String ratingProudct(RatingDTO ratingDTO) {
        int productId = ratingDTO.getProductId();
        String accountUsername = ratingDTO.getUsername();
        int point = ratingDTO.getPoint();

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(
            () -> new ApiRequestException(errorCode.getPRODUCT_NOT_FOUND())
        );
        if(productEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getPRODUCT_IS_DISABLED());
        }

        AccountEntity accountEntity = accountRepository.findById(accountUsername).orElseThrow(
            () -> new ApiRequestException(errorCode.getACCOUNT_NOT_FOUND())
        );
        if(accountEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getACCOUNT_IS_DISABLED());
        }

        if(String.valueOf(point) == null || !String.valueOf(point).matches("^[0-9]+$")
            || point < 1 || point > 10)
        {
            throw new ApiRequestException(errorCode.getPOINT_NOT_CORRECT());
        }

        Optional<RatingEntity> optional = ratingRepository.findByProductAndAccount(productEntity, accountEntity);
        if(optional.isPresent()){
            throw new ApiRequestException(errorCode.getRATING_ALREADY());
        }

        RatingEntity ratingEntity = new RatingEntity();
        if(ratingRepository.count() < 1){
            ratingEntity.setRatingId(1);
        }
        else{
            ratingEntity.setRatingId(ratingRepository.findMaxId() + 1);
        }
        ratingEntity.setProduct(productEntity);
        ratingEntity.setAccount(accountEntity);
        ratingEntity.setPoint(point);
        ratingRepository.save(ratingEntity);

        int totalPoint = 0;
        List<RatingEntity> ratingEntityList = ratingRepository.findByProduct(productEntity);
        for (RatingEntity rating : ratingEntityList) {
            totalPoint += rating.getPoint();
        }
        int averageRate;
        if(ratingEntityList.size() == 0){
            averageRate = 0;
        }
        else{
            averageRate = totalPoint / ratingEntityList.size();
        }

        productEntity.setAverageRate(averageRate);
        productRepository.save(productEntity);

        return "Rating successfully!";
    }

    @Override
    public Integer viewPoint(RatingDTO ratingDTO) {
        int productId = ratingDTO.getProductId();
        String accountUsername = ratingDTO.getUsername();

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(
            () -> new ApiRequestException(errorCode.getPRODUCT_NOT_FOUND())
        );
        if(productEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getPRODUCT_IS_DISABLED());
        }

        AccountEntity accountEntity = accountRepository.findById(accountUsername).orElseThrow(
            () -> new ApiRequestException(errorCode.getACCOUNT_NOT_FOUND())
        );
        if(accountEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getACCOUNT_IS_DISABLED());
        }

        Optional<RatingEntity> optional = ratingRepository.findByProductAndAccount(productEntity, accountEntity);
        if(optional.isPresent()){
            return optional.get().getPoint();
        }
        else{
            return -1;
        }
    }

    @Override
    public String updateRating(RatingDTO ratingDTO) {
        int productId = ratingDTO.getProductId();
        String accountUsername = ratingDTO.getUsername();
        int point = ratingDTO.getPoint();

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(
            () -> new ApiRequestException(errorCode.getPRODUCT_NOT_FOUND())
        );
        if(productEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getPRODUCT_IS_DISABLED());
        }

        AccountEntity accountEntity = accountRepository.findById(accountUsername).orElseThrow(
            () -> new ApiRequestException(errorCode.getACCOUNT_NOT_FOUND())
        );
        if(accountEntity.isDeleted()){
            throw new ApiRequestException(errorCode.getACCOUNT_IS_DISABLED());
        }

        if(String.valueOf(point) == null || !String.valueOf(point).matches("^[0-9]+$")
            || point < 1 || point > 10)
        {
            throw new ApiRequestException(errorCode.getPOINT_NOT_CORRECT());
        }

        Optional<RatingEntity> optional = ratingRepository.findByProductAndAccount(productEntity, accountEntity);
        if(optional.isPresent() == false) {
            throw new ApiRequestException(errorCode.getRATING_NOT_FOUND());
        }
        RatingEntity ratingEntity = optional.get();
        ratingEntity.setPoint(point);
        ratingRepository.save(ratingEntity);

        int totalPoint = 0;
        List<RatingEntity> ratingEntityList = ratingRepository.findByProduct(productEntity);
        for (RatingEntity rating : ratingEntityList) {
            totalPoint += rating.getPoint();
        }
        int averageRate;
        if(ratingEntityList.size() == 0){
            averageRate = 0;
        }
        else{
            averageRate = totalPoint / ratingEntityList.size();
        }

        productEntity.setAverageRate(averageRate);
        productRepository.save(productEntity);

        return "Update Rating Successfully!";
    }
    
}
