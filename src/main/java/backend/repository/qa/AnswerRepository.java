package backend.repository.qa;

import backend.model.qa.AnswerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Kevin Tan 2018-01-30
 */
public interface AnswerRepository extends JpaRepository<AnswerModel, Long> {

    Collection<AnswerModel> findByQuestionId(long questionId);
}
