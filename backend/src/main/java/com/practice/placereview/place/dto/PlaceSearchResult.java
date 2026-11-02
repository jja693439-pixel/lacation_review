package com.practice.placereview.place.dto;

public record PlaceSearchResult(
        String kakaoPlaceId,
        String name,
        String address,
        double x,
        double y,
        String category
) {
}
