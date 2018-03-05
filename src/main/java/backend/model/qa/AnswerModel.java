package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.user.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AnswerModel extends ForumPost {

    @ManyToOne
    @JsonIgnore
    private QuestionModel question;
    @ManyToOne
    @JsonIgnore
    private UserModel userAnswer;

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

    public void setUserAnswer(UserModel userModel) {
        this.userAnswer = userModel;
        author = userModel.getUsername();
    }

    public UserModel getUserAnswer() {
        return userAnswer;
    }

    public Long getQuestionModelId(){
        return question.getId();
    }

    public Long getUserId(){
        return userAnswer.getId();
    }

    public String getQuestionModelTitle(){
        return question.getQuestionTitle();
    }
}
