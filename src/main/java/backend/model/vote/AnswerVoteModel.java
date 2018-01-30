package backend.model.vote;

import backend.model.qa.AnswerModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Kevin Tan 2018-01-29
 */

@Entity
public class AnswerVoteModel  {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    AnswerModel answer;
    private int upVotes;
    private int downVotes;

    public AnswerVoteModel(AnswerModel answer) {
        this.answer = answer;
        upVotes = 1;
        downVotes = 0;
    }

    public AnswerVoteModel(){
    }

    public AnswerModel getAnswer() {
        return answer;
    }

    public long getId() {
        return id;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public int getTotalVotes() {
        return upVotes + downVotes;
    }

    public void decrementUpVotes() {
        upVotes--;
    }

    public void incrementUpVotes() {
        upVotes++;
    }

    public void decrementDownVotes() {
        downVotes--;
    }

    public void incrementDownVotes() {
        downVotes++;
    }
}
