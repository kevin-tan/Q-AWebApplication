package backend.model.qa;

import backend.model.qa.common.ForumPost;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class QuestionModel extends ForumPost {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Set<AnswerModel> answerModel = new HashSet<>();

    public QuestionModel(String message, String postedTime) {
        super(message, postedTime);
    }

    public QuestionModel() { //for jpa
        super("", "");
    }

    public Set<AnswerModel> getAnswerModel() {
        return answerModel;
    }

}
