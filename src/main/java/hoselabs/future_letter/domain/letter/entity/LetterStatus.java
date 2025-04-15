package hoselabs.future_letter.domain.letter.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LetterStatus {
    DRAFT("DRAFT", "임시저장"),
    SCHEDULED("SCHEDULED", "예약발송"),
    DELIVERED("DELIVERED", "도착완료(미열람)"),
    READ("READ", "도착완료(열람)");

    private final String key;
    private final String name;
}
