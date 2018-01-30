package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.vote.QuestionVoteModel;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class QuestionModel extends ForumPost{

    @Id
    private long id;
    @OneToMany
    private Set<AnswerModel> answers = new HashSet<>();
    @OneToOne
    private QuestionVoteModel votes;

    public QuestionModel(String postMessage, String author, DateTime dateTime) {
        super(postMessage, author, dateTime);
        id = 0; //dummy value, will always be changed
        votes = new QuestionVoteModel(this);
    }

    public QuestionModel(){ //for jpa
        this("","", new DateTime());
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Set<AnswerModel> getAnswers() {
        return answers;
    }

    public QuestionVoteModel getVotes() {
        return votes;
    }
}
