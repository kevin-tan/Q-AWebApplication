package backend.model.vote;

import backend.model.qa.common.ForumPost;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class VoteModel {

    @Id
    @GeneratedValue
    private long id;
    private int upVotes;
    private int downVotes;
    @OneToOne
    @JsonIgnore
    private ForumPost forumPost;

    public VoteModel(ForumPost forumPost) {
        this.forumPost = forumPost;
        upVotes = 1;
        downVotes = 0;
    }

    public VoteModel() {

    }

    public long getId() {
        return id;
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

    public ForumPost getForumPost() {
        return forumPost;
    }

    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }
}
