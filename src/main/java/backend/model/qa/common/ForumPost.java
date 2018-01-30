package backend.model.qa.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by Kevin Tan 2018-01-28
 */

public abstract class ForumPost {

    protected String message;
    protected DateTime dateTime;
    //TODO replace with User issue#8
    protected String author;

    public ForumPost(String message, String author, DateTime dateTime) {
        this.message = message;
        this.author = author;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime.toString(DateTimeFormat.mediumDateTime());
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

}
