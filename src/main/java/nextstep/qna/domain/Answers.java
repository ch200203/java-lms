package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Answers {

    private List<Answer> answers;

    public Answers(List<Answer> answers) {
        this.answers = new ArrayList<>(answers);
    }

    public void deleteAnswers(final NsUser nsUser) throws CannotDeleteException {
        for (Answer answer : answers) {
            answer.delete(nsUser);
        }
    }


}
