package backend.model.qa.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;

/**
 * Created by Kevin Tan 2018-01-30
 */

@Entity
@Inheritance
public abstract class ForumPost {

    @Id
    @JsonIgnore
    private long id;
    @JsonIgnore
    private String dateTime;
    private String message;
    //TODO replace with User
    private String author;

    public ForumPost(String message, String author, String dateTime){
        this.message = message;
        this.author = author;
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
