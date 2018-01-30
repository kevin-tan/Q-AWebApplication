package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.vote.QuestionVoteModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class QuestionModel extends ForumPost {

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AnswerModel> answerModel = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private QuestionVoteModel votes;

    public QuestionModel(String message, String author, String postedTime) {
        super(message, author, postedTime);
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
