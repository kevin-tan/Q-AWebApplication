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
    private Long id;
    @OneToOne
    @JsonIgnore
    private ForumPost forumPost;
    @ManyToMany
    @JsonIgnore
    private Set<UserModel> upVotedUsers = new HashSet<>();
    @ManyToMany
    @JsonIgnore
    private Set<UserModel> downVotedUsers = new HashSet<>();

    public VoteModel(ForumPost forumPost, UserModel user) {
        this.forumPost = forumPost;
        upVotedUsers.add(user);
    }

    public VoteModel() {
    }

    public Long getId() {
        return id;
    }

    public int getUpVotes() {
        return upVotedUsers.size();
    }

    public int getDownVotes() {
        return downVotedUsers.size();
    }

    public int getTotalVotes() {
        return upVotedUsers.size() + downVotedUsers.size();
    }

    public boolean incrementUpVotes(UserModel user) {
        return upVotedUsers.add(user);
    }

    public boolean incrementDownVotes(UserModel user) {
        return downVotedUsers.add(user);
    }

    public boolean decrementUpVotes(UserModel user) {
        return upVotedUsers.remove(user);
    }

    public boolean decrementDownVotes(UserModel user) {
        return downVotedUsers.remove(user);
    }

    public ForumPost getForumPost() {
        return forumPost;
    }

    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }

    public Set<UserModel> getUpVotedUsers() {
        return upVotedUsers;
    }

    public Set<UserModel> getDownVotedUsers() {
        return downVotedUsers;
    }
}
