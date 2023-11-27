package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteHistories {

    private List<DeleteHistory> deleteHistories;

    public DeleteHistories(Question question) {
        this.deleteHistories = create(question);
    }

    public List<DeleteHistory> getDeleteHistories() {
        return Collections.unmodifiableList(deleteHistories);
    }

    private List<DeleteHistory> create(final Question question) {
        ArrayList<DeleteHistory> deleteHistories = new ArrayList<>();

        deleteHistories
            .add(new DeleteHistory(ContentType.QUESTION, question));

        question.getAnswers().stream()
            .forEach(answer -> deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer)));

        return deleteHistories;
    }
}
