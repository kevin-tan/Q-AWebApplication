package backend.model.vote.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

/**
 * Created by Kevin Tan 2018-01-30
 */
@Entity
@Inheritance
public abstract class VoteModel {

    @Id
    @GeneratedValue
    private long id;
    private int upVotes;
    private int downVotes;

    protected VoteModel(){
        upVotes = 1;
        downVotes =0;
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
