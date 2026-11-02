package com.practice.placereview.place.dto;

import com.practice.placereview.place.Place;

public record PlaceResponse(
        Long id,
        String kakaoPlaceId,
        String name,
        String address,
        double x,
        double y,
        String category
) {

    public static PlaceResponse from(Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getKakaoPlaceId(),
                place.getName(),
                place.getAddress(),
                place.getX(),
                place.getY(),
                place.getCategory()
        );
    }
}
