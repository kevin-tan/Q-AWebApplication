package backend.model.qa.common;

import backend.model.vote.VoteModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updatedTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String postedDate;
    private String message;
    protected String author;
    @OneToOne(cascade = CascadeType.ALL)
    private VoteModel votes;

    public ForumPost(String message, String postedDate) {
        this.message = message;
        this.postedDate = postedDate;
        this.updatedTime = postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public VoteModel getVotes() {
        return votes;
    }

    public void setVotes(VoteModel votes) {
        this.votes = votes;
    }

}
