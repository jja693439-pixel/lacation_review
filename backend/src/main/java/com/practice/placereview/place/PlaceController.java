package com.practice.placereview.place;

import com.practice.placereview.place.dto.PlaceResponse;
import com.practice.placereview.place.dto.PlaceSearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final KakaoPlaceClient kakaoPlaceClient;
    private final PlaceService placeService;

    public PlaceController(KakaoPlaceClient kakaoPlaceClient, PlaceService placeService) {
        this.kakaoPlaceClient = kakaoPlaceClient;
        this.placeService = placeService;
    }

    @GetMapping("/search")
    public List<PlaceSearchResult> search(@RequestParam("q") String query) {
        return kakaoPlaceClient.search(query);
    }

    @PostMapping
    public PlaceResponse save(@RequestBody PlaceSearchResult place) {
        return PlaceResponse.from(placeService.save(place));
    }

    @GetMapping("/{id}")
    public PlaceResponse get(@PathVariable Long id) {
        return PlaceResponse.from(placeService.getById(id));
    }

    @GetMapping
    public List<PlaceResponse> list() {
        return placeService.findAll().stream()
                .map(PlaceResponse::from)
                .toList();
    }
}
