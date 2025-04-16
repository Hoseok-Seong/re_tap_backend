package hoselabs.future_letter.domain.letter.service;

import hoselabs.future_letter.domain.letter.dao.LetterRepository;
import hoselabs.future_letter.domain.letter.dto.LetterCreateReq;
import hoselabs.future_letter.domain.letter.dto.LetterCreateResp;
import hoselabs.future_letter.domain.letter.dto.LetterListResp;
import hoselabs.future_letter.domain.letter.entity.Letter;
import hoselabs.future_letter.domain.letter.entity.LetterStatus;
import hoselabs.future_letter.domain.setup.MockTest;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.global.error.exception.UserDetailsException;
import hoselabs.future_letter.global.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class LetterServiceTest extends MockTest {

    @InjectMocks
    private LetterService letterService;

    @Mock
    private LetterRepository letterRepository;

    private User user;
    private MyUserDetails userDetails;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .provider("kakao")
                .build();

        userDetails = new MyUserDetails(user);
    }

    @Test
    void 편지_작성_성공() {
        // given
        LetterCreateReq req = new LetterCreateReq("제목", "내용", false, LocalDateTime.now(), true);

        Letter saved = Letter.builder()
                .id(123L)
                .userId(user.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .status(LetterStatus.DELIVERED)
                .build();

        given(letterRepository.save(any())).willReturn(saved);

        // when
        LetterCreateResp result = letterService.createLetter(userDetails, req);

        // then
        assertThat(result.getLetterId()).isEqualTo(123L);
    }

    @Test
    void 유저정보가_없으면_예외() {
        // given
        LetterCreateReq req = new LetterCreateReq("제목", "내용", false, LocalDateTime.now(), true);

        // when & then
        assertThatThrownBy(() -> letterService.createLetter(null, req))
                .isInstanceOf(UserDetailsException.class);
    }

    @Test
    void 편지_목록_조회_성공() {
        // given
        Letter unreadLetter = Letter.builder()
                .id(1L)
                .userId(user.getId())
                .title("첫 번째 편지")
                .arrivalDate(LocalDateTime.now().minusDays(1))
                .isLocked(false)
                .build(); // readAt = null → 읽지 않음

        Letter readLetter = Letter.builder()
                .id(2L)
                .userId(user.getId())
                .title("두 번째 편지")
                .arrivalDate(LocalDateTime.now().plusDays(1))
                .isLocked(true)
                .build();

        // 읽음 처리
        readLetter.markAsRead(); // 읽은 상태로 처리

        List<Letter> letters = List.of(unreadLetter, readLetter);

        given(letterRepository.findAllByUserIdSorted(user.getId())).willReturn(letters);

        // when
        LetterListResp result = letterService.getLetters(user.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getLetters()).hasSize(2);

        LetterListResp.LetterSummary first = result.getLetters().get(0);
        assertThat(first.getLetterId()).isEqualTo(1L);
        assertThat(first.getTitle()).isEqualTo("첫 번째 편지");
        assertThat(first.getIsRead()).isFalse();
        assertThat(first.getIsArrived()).isTrue();
    }
}


