package backend.model.vote;

import backend.model.qa.AnswerModel;
import backend.model.vote.common.VoteModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Kevin Tan 2018-01-29
 */

@Entity
public class AnswerVoteModel extends VoteModel {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    AnswerModel answerModel;

    public AnswerVoteModel(AnswerModel answerModel) {
        this.answerModel = answerModel;
    }

    public AnswerModel getAnswerModel() {
        return answerModel;
    }

    public long getId() {
        return id;
    }
}
