package backend.model.vote;

import backend.model.qa.QuestionModel;

import javax.persistence.*;

/**
 * Created by Kevin Tan 2018-01-29
 */

@Entity
public class QuestionVoteModel {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    QuestionModel question;
    private int upVotes;
    private int downVotes;

    public QuestionVoteModel(QuestionModel question) {
        this.question = question;
        upVotes = 1;
        downVotes = 0;
    }

    public QuestionVoteModel(){
    }

    public QuestionModel getQuestion() {
        return question;
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
