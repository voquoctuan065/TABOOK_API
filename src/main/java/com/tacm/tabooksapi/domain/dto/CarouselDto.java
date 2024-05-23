package com.tacm.tabooksapi.domain.dto;

import com.tacm.tabooksapi.domain.entities.Carousel;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarouselDto {
    private Long carouselId;
    private String imageUrl;
    private String targetUrl;
    private LocalDateTime createdAt;

    public static CarouselDto fromEntity(Carousel carousel) {
        return new CarouselDto(
                carousel.getCarouselId(),
                carousel.getImageUrl(),
                carousel.getTargetUrl(),
                carousel.getCreatedAt()
        );
    }
    public static Carousel toDto(CarouselDto carouselDto) {
        return new Carousel(
                carouselDto.getCarouselId(),
                carouselDto.getImageUrl(),
                carouselDto.getTargetUrl(),
                carouselDto.getCreatedAt()
        );
    }
}
