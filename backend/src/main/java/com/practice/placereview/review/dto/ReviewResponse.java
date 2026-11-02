package com.practice.placereview.review.dto;

import com.practice.placereview.review.Review;

import java.time.LocalDate;

public record ReviewResponse(
        Long id,
        Long placeId,
        String review,
        LocalDate visitDate,
        int rating
) {

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getPlace().getId(),
                review.getReview(),
                review.getVisitDate(),
                review.getRating()
        );
    }
}
