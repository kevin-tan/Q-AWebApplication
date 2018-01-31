package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.user.UserModel;
import backend.model.vote.QuestionVoteModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class QuestionModel extends ForumPost {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<AnswerModel> answerModel = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private QuestionVoteModel votes;
    @ManyToOne
    @JsonIgnore
    private UserModel userModel;

    public QuestionModel(String message, String postedTime) {
        super(message, postedTime);
    }

    public QuestionModel() { //for jpa
        super("", "");
    }

    public void setVotes(QuestionVoteModel votes) {
        this.votes = votes;
    }

    public Set<AnswerModel> getAnswerModel() {
        return answerModel;
    }

    public QuestionVoteModel getVotes() {
        return votes;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
