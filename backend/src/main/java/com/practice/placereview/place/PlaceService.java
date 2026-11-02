package com.practice.placereview.place;

import com.practice.placereview.place.dto.PlaceSearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Place save(PlaceSearchResult result) {
        return placeRepository.findByKakaoPlaceId(result.kakaoPlaceId())
                .orElseGet(() -> placeRepository.save(new Place(
                        null,
                        result.kakaoPlaceId(),
                        result.name(),
                        result.address(),
                        result.x(),
                        result.y(),
                        result.category()
                )));
    }

    public Place getById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("장소를 찾을 수 없습니다. id=" + id));
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }
}
