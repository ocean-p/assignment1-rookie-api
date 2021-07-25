package com.daiduong.demo.service.interfaces;

import com.daiduong.demo.dto.RatingDTO;

public interface IRatingService {
    
    public String ratingProudct(RatingDTO ratingDTO);

    public Integer viewPoint(RatingDTO ratingDTO);

    public String updateRating(RatingDTO ratingDTO);
}
