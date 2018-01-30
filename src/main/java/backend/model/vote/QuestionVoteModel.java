package backend.model.vote;

import backend.model.qa.QuestionModel;
import backend.model.vote.common.VoteModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Kevin Tan 2018-01-29
 */

@Entity
public class QuestionVoteModel extends VoteModel {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    QuestionModel question;

    public QuestionVoteModel(QuestionModel question) {
        this.question = question;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public long getId() {
        return id;
    }
}
