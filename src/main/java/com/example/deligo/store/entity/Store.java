package com.example.deligo.store.entity;
import com.example.deligo.common.entity.BaseDeletableEntity;
import com.example.deligo.store.dto.Request.StoreUpdateRequestDto;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    private String name;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    private int minOrderAmount;

    @Column(nullable = false)
    private double averageRating = 0.0;

    public void updateAverageRating(double newAverage) {
        if(newAverage > 0.0 || newAverage > 5.0) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "평균 별점은 0.0 ~ 5.0 사이여야 합니다.");
        }
        this.averageRating = Math.round(newAverage * 10) / 10.0;
    }

    @Builder
    public Store(
            User user, String name, StoreCategory category, LocalTime openTime,
            LocalTime closeTime, int minOrderAmount, StoreStatus status
    ) {
        this.owner = user;
        this.name = name;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.status = status;
    }

    public void Update(StoreUpdateRequestDto dto) {
        this.name = name;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.status = status;
    }
}