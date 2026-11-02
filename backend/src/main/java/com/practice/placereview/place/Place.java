package com.practice.placereview.place;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "place")
@Getter
@Setter
@NoArgsConstructor   // JPA는 기본 생성자가 반드시 필요함
@AllArgsConstructor  // 필드 다 채우는 생성자 (선택, 있으면 편함)

public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String kakaoPlaceId;

    private String name;
    private String address;
    private double x; // 경도
    private double y; // 위도
    private String category;
}