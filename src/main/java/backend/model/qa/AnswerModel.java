package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.vote.AnswerVoteModel;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class AnswerModel extends ForumPost {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private AnswerVoteModel votes;
    @ManyToOne
    private QuestionModel question;

    public AnswerModel(QuestionModel question, String postMessage, String author, DateTime dateTime) {
        super(postMessage, author, dateTime);
        votes = new AnswerVoteModel(this);
        this.question = question;
    }

    public AnswerModel() { //for jpa
        this(new QuestionModel(), "", "", new DateTime());
    }

    public long getId() {
        return id;
    }

    public AnswerVoteModel getVotes() {
        return votes;
    }

    public QuestionModel getQuestion() {
        return question;
    }
}
