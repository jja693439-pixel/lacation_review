package com.practice.placereview.review;
import jakarta.persistence.*;
import lombok.*;
import com.practice.placereview.place.Place;
import java.time.*;
@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor   // JPA는 기본 생성자가 반드시 필요함
@AllArgsConstructor  // 필드 다 채우는 생성자 (선택, 있으면 편함)

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private String review;
    private LocalDate visitDate;
    private int rating;
}