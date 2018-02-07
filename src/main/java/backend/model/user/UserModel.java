package backend.model.user;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.vote.VoteModel;
import backend.model.roles.RoleModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //User information
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dateJoined;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int reputation;

    //Questions & Answers
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userQuestion")
    private Set<QuestionModel> questionModels = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAnswer")
    private Set<AnswerModel> answerModels = new HashSet<>();

    //Roles
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<RoleModel> roles = new HashSet<>();

    //Votes
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "upVotedUsers")
    @JsonIgnore
    private Set<VoteModel> upVotedVoteModels = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "downVotedUsers")
    @JsonIgnore
    private Set<VoteModel> downVotedVoteModels = new HashSet<>();

    //User credentials
    @JsonIgnore
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public UserModel(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        reputation = 0;
    }

    public UserModel() {
        this("", "", "", "", "");
    }

    public Long getId() {
        return id;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public int getReputation() {
        return reputation;
    }

    public void incrementReputation() {
        reputation++;
    }

    public void decrementReputation() {
        reputation--;
    }

    public Set<QuestionModel> getQuestionModels() {
        return questionModels;
    }

    public Set<AnswerModel> getAnswerModels() {
        return answerModels;
    }

    public Set<RoleModel> getRoleModel() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addRole(RoleModel roleModel) {
        roles.add(roleModel);
    }

    public void removeRole(RoleModel roleModel) {
        roles.remove(roleModel);
    }
}
