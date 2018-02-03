package backend.model.qa;

import backend.model.qa.common.ForumPost;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class AnswerModel extends ForumPost {

    @ManyToOne
    @JsonIgnore
    private QuestionModel question;

    public AnswerModel(QuestionModel question, String replyMessage, String postedTime) {
        super(replyMessage, postedTime);
        this.question = question;
    }

    public AnswerModel() {
        super("", "");
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public QuestionModel getQuestion() {
        return question;
    }

}
