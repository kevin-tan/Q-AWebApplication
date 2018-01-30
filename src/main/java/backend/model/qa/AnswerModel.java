package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.vote.AnswerVoteModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class AnswerModel extends ForumPost {

    @Id
    private long id;
    @OneToOne
    private AnswerVoteModel votes;
    @ManyToOne
    private QuestionModel question;

    public AnswerModel(QuestionModel question, String postMessage, String author, String dateTime) {
        super(postMessage, author, dateTime);
        id = 0;
        this.question = question;
    }

    public AnswerModel() { //for jpa
        this(null, "", "", "");
    }

    public void setId(long id){
        this.id = id;
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
