package backend.model.vote;

import backend.model.qa.QuestionModel;
import backend.model.vote.common.VoteModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Kevin Tan 2018-01-29
 */

@Entity
public class QuestionVoteModel extends VoteModel{

    @OneToOne
    @JsonIgnore
    QuestionModel question;

    public QuestionVoteModel(QuestionModel question) {
        this.question = question;
    }

    public QuestionVoteModel(){
    }

    public QuestionModel getQuestion() {
        return question;
    }

}
