package backend.model.qa;

import backend.model.vote.QuestionVoteModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kevin Tan 2018-01-28
 */

@Entity
public class QuestionModel{

    @Id
    @JsonIgnore
    private long id;
    @OneToMany
    @JsonIgnore
    private Set<AnswerModel> answers = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private QuestionVoteModel votes;
    @JsonIgnore
    private String dateTime;

    //TODO replace with User issue#8
    private String author;
    private String postMessage;

    public QuestionModel(String postMessage, String author, String dateTime) {
        this.postMessage= postMessage;
        this.author = author;
        this.dateTime = dateTime;
    }

    public QuestionModel(){ //for jpa

    }

    public void setVotes(QuestionVoteModel votes){
        this.votes = votes;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Set<AnswerModel> getAnswers() {
        return answers;
    }

    public QuestionVoteModel getVotes() {
        return votes;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
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
