package com.practice.placereview.review;

import com.practice.placereview.place.Place;
import com.practice.placereview.place.PlaceService;
import com.practice.placereview.review.dto.ReviewRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;

    public ReviewService(ReviewRepository reviewRepository, PlaceService placeService) {
        this.reviewRepository = reviewRepository;
        this.placeService = placeService;
    }

    public Review create(Long placeId, ReviewRequest request) {
        Place place = placeService.getById(placeId);
        Review review = new Review(null, place, request.review(), request.visitDate(), request.rating());
        return reviewRepository.save(review);
    }

    public List<Review> findByPlace(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }

    public Review update(Long reviewId, ReviewRequest request) {
        Review review = getById(reviewId);
        review.setReview(request.review());
        review.setVisitDate(request.visitDate());
        review.setRating(request.rating());
        return reviewRepository.save(review);
    }

    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다. id=" + id));
    }
}
