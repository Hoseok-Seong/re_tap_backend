package hoselabs.future_letter.domain.letter.dto;

import hoselabs.future_letter.domain.letter.entity.Letter;
import hoselabs.future_letter.domain.letter.entity.LetterStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor // 테스트 용도
public class LetterCreateReq {
    private Long letterId;
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    private Boolean isLocked;
    private LocalDateTime arrivalDate;
    @NotNull(message = "isSend 값은 필수입니다.")
    private Boolean isSend;

    public Letter toEntity(Long userId, LetterStatus status) {
        return Letter.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .isLocked(isLocked)
                .arrivalDate(arrivalDate)
                .status(status)
                .build();
    }

    public void updateEntity(Letter letter, LetterStatus status) {
        letter.update(
                title,
                content,
                isLocked,
                arrivalDate,
                status
        );
    }
}
