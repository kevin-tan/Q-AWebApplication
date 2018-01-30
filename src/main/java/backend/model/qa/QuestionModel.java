package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.vote.QuestionVoteModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class QuestionModel extends ForumPost {

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AnswerModel> answerModel = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private QuestionVoteModel votes;

    public QuestionModel(String message, String author, String dateTime) {
        super(message, author, dateTime);
    }

    public QuestionModel() { //for jpa
        super("", "", "");
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
}
