package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.user.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class QuestionModel extends ForumPost {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<AnswerModel> answerModel = new HashSet<>();
    @ManyToOne
    @JsonIgnore
    private UserModel userQuestion;

    @ElementCollection
    private Set<String> questionCategories = new HashSet<>();

    private String questionTitle;
    private Long bestAnswerId;

    public QuestionModel(String questionTitle, String message, Set<String> categories, String postedTime) {
        super(message, postedTime);
        this.questionTitle = questionTitle;
        questionCategories.addAll(categories);
    }

    public void setBestAnswer(long id){
    	bestAnswerId = id;
    }
    
    public Long getBestAnswer(){
    	return bestAnswerId;
    }

    public QuestionModel() { //for jpa
        super("", "");
    }

    public Set<AnswerModel> getAnswerModel() {
        return answerModel;
    }

    public void setUserQuestion(UserModel userModel) {
        this.userQuestion = userModel;
        author = userModel.getUsername();
    }

    public UserModel getUserQuestion() {
        return userQuestion;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Set<String> getQuestionCategories() {
        return questionCategories;
    }

    public void setQuestionCategories(Set<String> questionCategories) {
        this.questionCategories = questionCategories;
    }

}
