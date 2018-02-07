package backend.model.user;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.vote.VoteModel;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dateJoined;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int reputation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userQuestion")
    private Set<QuestionModel> questionModels = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAnswer")
    private Set<AnswerModel> answerModels = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "upVotedUsers")
    @JsonIgnore
    private Set<VoteModel> upVotedVoteModels = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "downVotedUsers")
    @JsonIgnore
    private Set<VoteModel> downVotedVoteModels = new HashSet<>();

    @JsonIgnore
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    //TODO
    //private String role;

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
}
