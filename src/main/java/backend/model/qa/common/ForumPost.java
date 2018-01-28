package backend.model.qa.common;

import backend.model.vote.VoteModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by Kevin Tan 2018-01-28
 */

public abstract class ForumPost {

    protected String postMessage;
    protected DateTime dateTime;
    //TODO figure out if Vote model should be implemented to hold these values issue#12
    protected final VoteModel votes;
    //TODO replace with User
    protected String author;

    public ForumPost(String postMessage, String author, DateTime dateTime, VoteModel votes) {
        this.postMessage = postMessage;
        this.author = author;
        this.dateTime = dateTime;
        this.votes = votes;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public String getDateTime() {
        return dateTime.toString(DateTimeFormat.mediumDateTime());
    }

    public int getUpVotes(){
        return votes.getUpVotes();
    }

    public int getTotalVotes(){
        return votes.getTotalVotes();
    }

    public int getDownVotes(){
        return votes.getDownVotes();
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void incrementUpVote(){
        votes.incrementDownVotes();
    }

    public void decrementUpVote(){
        votes.decrementUpVotes();
    }

    public void incrementDownVote(){
        votes.incrementDownVotes();
    }

    public void decrementDownVote(){
        votes.decrementDownVotes();
    }
}
