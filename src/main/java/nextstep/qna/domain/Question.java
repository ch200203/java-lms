package nextstep.qna.domain;

import java.util.Objects;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {

    private Long id;

    // title - content -> Body 라는 객체로 묶어도 좋음
    private String title;

    private String contents;

    private NsUser writer;

    private List<Answer> answers = new ArrayList<>();

    private Answers questionAnswers = new Answers(answers);

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Question(NsUser writer, String title, String contents, boolean deleted) {
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Question setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public Question setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public DeleteHistories deleteQuestion(NsUser loginUser) throws CannotDeleteException{
        validateDeleteQuestion(loginUser);
        delete();
        for (Answer answer : answers) {
            answer.delete(loginUser);
        }
        // questionAnswers.deleteAnswers(loginUser);
        return new DeleteHistories(this);
    }

    private void validateDeleteQuestion(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private boolean isSameAnswerOwner(NsUser loginUser) {
        return answers.stream().allMatch(answer -> answer.isOwner(loginUser));
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isNoAnswers() {
        return answers.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return deleted == question.deleted && Objects.equals(id, question.id)
            && Objects.equals(title, question.title) && Objects.equals(contents,
            question.contents) && Objects.equals(writer, question.writer)
            && Objects.equals(answers, question.answers) && Objects.equals(
            createdDate, question.createdDate) && Objects.equals(updatedDate,
            question.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, contents, writer, answers, deleted, createdDate,
            updatedDate);
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents
            + ", writer=" + writer + "]";
    }

    public boolean isSameWriter() {
        return answers.stream()
            .allMatch(answer -> answer.isOwner(writer));
    }
}
