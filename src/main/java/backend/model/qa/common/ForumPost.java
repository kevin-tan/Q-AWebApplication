package backend.model.qa.common;

/**
 * Created by Kevin Tan 2018-01-28
 */

public abstract class ForumPost {

    protected String message;
    protected String dateTime;
    //TODO replace with User issue#8
    protected String author;

    public ForumPost(String message, String author, String dateTime) {
        this.message = message;
        this.author = author;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthor(){
        return author;
    }

}
