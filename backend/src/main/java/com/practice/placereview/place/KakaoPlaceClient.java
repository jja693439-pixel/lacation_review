package com.practice.placereview.place;

import com.practice.placereview.place.dto.KakaoSearchResponse;
import com.practice.placereview.place.dto.PlaceSearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class KakaoPlaceClient {

    private static final String SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    private final RestClient restClient = RestClient.create();

    @Value("${kakao.rest-api-key}")
    private String kakaoApiKey;

    public List<PlaceSearchResult> search(String query) {
        KakaoSearchResponse response = restClient.get()
                .uri(SEARCH_URL + "?query={query}", query)
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .body(KakaoSearchResponse.class);

        if (response == null) {
            return List.of();
        }

        return response.documents().stream()
                .map(doc -> new PlaceSearchResult(
                        doc.id(),
                        doc.placeName(),
                        doc.addressName(),
                        Double.parseDouble(doc.x()),
                        Double.parseDouble(doc.y()),
                        doc.categoryName()
                ))
                .toList();
    }
}
