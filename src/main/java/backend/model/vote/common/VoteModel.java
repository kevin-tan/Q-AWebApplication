package backend.model.vote.common;

/**
 * Created by Kevin Tan 2018-01-28
 */

public abstract class VoteModel {

    protected int upVotes;
    protected int downVotes;

    public VoteModel() {
        upVotes = 1;
        downVotes = 0;
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
