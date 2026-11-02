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
        if (kakaoApiKey == null || kakaoApiKey.isBlank()) {
            throw new IllegalStateException(
                    "KAKAO_REST_API_KEY가 설정되지 않았습니다. 프로젝트 루트의 .env 파일(.env.example 참고)에 키를 넣어 주세요.");
        }

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
