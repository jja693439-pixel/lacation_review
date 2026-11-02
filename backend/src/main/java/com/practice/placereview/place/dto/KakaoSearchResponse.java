package com.practice.placereview.place.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoSearchResponse(List<KakaoDocument> documents) {
}
