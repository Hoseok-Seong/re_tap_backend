package hoselabs.future_letter.domain.letter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LetterCreateReq {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    private Boolean isLocked;
    private LocalDateTime arrivalDate;
    @NotNull(message = "isSend 값은 필수입니다.")
    private Boolean isSend;
}
