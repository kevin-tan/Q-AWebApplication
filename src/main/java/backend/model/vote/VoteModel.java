package backend.model.vote;

import backend.model.qa.common.ForumPost;
import backend.model.user.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class VoteModel {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JsonIgnore
    private ForumPost forumPost;
    @ManyToMany
    @JsonIgnore
    private Set<UserModel> userUpVotes = new HashSet<>();
    @ManyToMany
    @JsonIgnore
    private Set<UserModel> userDownVotes = new HashSet<>();

    public VoteModel(ForumPost forumPost, UserModel user) {
        this.forumPost = forumPost;
        userUpVotes.add(user);
    }

    public VoteModel() {

    }

    public long getId() {
        return id;
    }

    public int getUpVotes() {
        return userUpVotes.size();
    }

    public int getDownVotes() {
        return userDownVotes.size();
    }

    public int getTotalVotes() {
        return userUpVotes.size() + userDownVotes.size();
    }

    public boolean incrementUpVotes(UserModel user) {
        return userUpVotes.add(user);
    }

    public boolean incrementDownVotes(UserModel user) {
        return userDownVotes.add(user);
    }

    public void decrementUpVotes(UserModel user) {
        userUpVotes.remove(user);
    }

    public void decrementDownVotes(UserModel user) {
        userDownVotes.remove(user);
    }

    public ForumPost getForumPost() {
        return forumPost;
    }

    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }

    public Set<UserModel> getUserUpVotes() {
        return userUpVotes;
    }

    public Set<UserModel> getUserDownVotes() {
        return userDownVotes;
    }
}
