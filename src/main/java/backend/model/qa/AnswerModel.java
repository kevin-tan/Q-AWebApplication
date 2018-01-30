package backend.model.qa;

import backend.model.vote.AnswerVoteModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class AnswerModel{

    @Id
    @JsonIgnore
    private long id;
    @OneToOne
    @JsonIgnore
    private AnswerVoteModel votes;
    @ManyToOne
    @JsonIgnore
    private QuestionModel question;
    @JsonIgnore
    private String dateTime;

    private String replyMessage;
    //TODO replace with User
    private String author;

    public AnswerModel(QuestionModel question, String replyMessage, String author, String dateTime) {
        this.replyMessage = replyMessage;
        this.author = author;
        this.dateTime = dateTime;
        this.question = question;
    }

    public AnswerModel() { //for jpa
        this(null, "", "", "");
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setVotes(AnswerVoteModel votes){
        this.votes = votes;
    }

    public AnswerVoteModel getVotes() {
        return votes;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
