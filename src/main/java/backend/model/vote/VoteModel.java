package backend.model.vote;

/**
 * Created by Kevin Tan 2018-01-28
 */
public class VoteModel {

    private int upVotes;
    private int downVotes;

    public VoteModel(){
        upVotes = 1;
        downVotes = 0;
    }

    public VoteModel(int upVotes, int downVotes) {
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public int getTotalVotes(){
        return upVotes + downVotes;
    }

    public void decrementUpVotes(){
        upVotes--;
    }

    public void incrementUpVotes() {
        upVotes++;
    }

    public void decrementDownVotes(){
        downVotes--;
    }

    public void incrementDownVotes() {
        downVotes++;
    }

}
