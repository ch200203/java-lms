package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuestionTest {

    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");
    public static final Question Q3 = new Question(NsUserTest.SANJIGI, "title2", "contents2",
        false);

    public static final Question Q4 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

    @Test
    @DisplayName("질문을 삭제할 경우 delete의 상태값이 true가 된다.")
    void deleteQuestionStatusIsFalse() {
        Q1.delete();
        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("답변 없는경우에 true를 반환한다")
    void 답변_없음() {
        // assertThat(Q1.isNoAnswers()).isTrue();
    }

    @Test
    @DisplayName("질문자 == 답변자인지 확인하다.")
    void 질문자_답변자_같은지_확인한다() {
        Q4.addAnswer(AnswerTest.A3);
        assertThat(Q4.isSameWriter()).isTrue();
    }

    @Test
    @DisplayName("질문자_답변자가_다른경우_FALSE")
    void 질문자_답변자가_다른경우_FALSE() {
        Q4.addAnswer(AnswerTest.A2);
        assertThat(Q4.isSameWriter()).isFalse();
    }

    @Test
    @DisplayName("질문자 != 답변자 인경우 삭제가 불가능하다.")
    void 삭제_불가능() {
        Q2.addAnswer(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q2, "Answers Contents1"));
        assertThatThrownBy(() -> Q2.deleteQuestion(NsUserTest.SANJIGI))
            .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문을 삭제할때, 답변도 같이 삭제 되어야 한다.")
    void 질문삭제시_답변도_같이_삭제() throws Exception {
        Answer answers_contents1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1,
            "Answers Contents1");
        Q1.addAnswer(answers_contents1);
        Q1.deleteQuestion(NsUserTest.JAVAJIGI);
        assertThat(Q1.isDeleted()).isTrue();
        assertThat(answers_contents1.isDeleted()).isTrue();
    }
}
