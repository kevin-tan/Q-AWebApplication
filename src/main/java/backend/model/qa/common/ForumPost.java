package backend.model.qa.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by Kevin Tan 2018-01-28
 */

public abstract class ForumPost {

    protected String postMessage;
    protected DateTime dateTime;
    //TODO figure out if Vote model should be implemented to hold these values issue#12
    protected int upVotes, downVotes;
    //TODO replace with User
    protected String author;

    public ForumPost(String postMessage, String author, DateTime dateTime, int upVotes, int downVotes) {
        this.postMessage = postMessage;
        this.author = author;
        this.dateTime = dateTime;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public String getDateTime() {
        return dateTime.toString(DateTimeFormat.mediumDateTime());
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
