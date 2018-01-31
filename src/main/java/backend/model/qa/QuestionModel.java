package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.vote.QuestionVoteModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
public class QuestionModel extends ForumPost {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<AnswerModel> answerModel = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
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
