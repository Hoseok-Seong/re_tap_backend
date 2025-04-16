package hoselabs.future_letter.domain.letter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterCreateResp {
    private Long letterId;

    public LetterCreateResp(Long letterId) {
        this.letterId = letterId;
    }
}
