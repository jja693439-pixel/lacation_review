package com.practice.placereview.review;

import com.practice.placereview.review.dto.ReviewRequest;
import com.practice.placereview.review.dto.ReviewResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/api/places/{placeId}/reviews")
    public ReviewResponse create(@PathVariable Long placeId, @RequestBody ReviewRequest request) {
        return ReviewResponse.from(reviewService.create(placeId, request));
    }

    @GetMapping("/api/places/{placeId}/reviews")
    public List<ReviewResponse> list(@PathVariable Long placeId) {
        return reviewService.findByPlace(placeId).stream()
                .map(ReviewResponse::from)
                .toList();
    }

    @PutMapping("/api/reviews/{id}")
    public ReviewResponse update(@PathVariable Long id, @RequestBody ReviewRequest request) {
        return ReviewResponse.from(reviewService.update(id, request));
    }

    @DeleteMapping("/api/reviews/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
