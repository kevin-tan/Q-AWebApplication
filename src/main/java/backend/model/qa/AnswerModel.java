package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.user.UserModel;
import backend.model.vote.AnswerVoteModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class AnswerModel extends ForumPost {

    @OneToOne(cascade = CascadeType.ALL)
    private AnswerVoteModel votes;
    @ManyToOne
    @JsonIgnore
    private QuestionModel question;
    @ManyToOne
    @JsonIgnore
    private UserModel user;
    private String author;

    public AnswerModel(QuestionModel question, String replyMessage, String postedTime) {
        super(replyMessage, postedTime);
        this.question = question;
        this.author = "";
    }

    public AnswerModel() {
        super("", "");
    }

    public void setVotes(AnswerVoteModel votes) {
        this.votes = votes;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public AnswerVoteModel getVotes() {
        return votes;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
        author = userModel.getUsername();
    }

    public String getAuthor() {
        return author;
    }

}
