package backend.model.qa;

import backend.model.qa.common.ForumPost;
import backend.model.user.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class QuestionModel extends ForumPost {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<AnswerModel> answerModel = new HashSet<>();
    @ManyToOne
    @JsonIgnore
    private UserModel userQuestion;

    private String questionTitle;
    private Long bestAnswerId;

    public QuestionModel(String questionTitle, String message, String postedTime) {
        super(message, postedTime);
        this.questionTitle = questionTitle;
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
}
