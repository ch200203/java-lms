package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteHistoryTest {

    @Test
    @DisplayName("삭제된 질문을 DeletHistory에 기록한다.")
    void deleteQuestionAddDeleteHistory() {
        Question q = QuestionTest.Q1;
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, q);
        // 질문 -> 기록
        // 답변을 -> 기록
        // DeleteHistories deleteHistories = new DeleteHistories();
    }

}
