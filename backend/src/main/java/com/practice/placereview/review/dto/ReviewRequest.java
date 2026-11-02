package com.practice.placereview.review.dto;

import java.time.LocalDate;

public record ReviewRequest(
        String review,
        LocalDate visitDate,
        int rating
) {
}
