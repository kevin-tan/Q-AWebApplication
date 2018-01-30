package backend.repository.qa;

import backend.model.qa.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kevin Tan 2018-01-29
 */

public interface QuestionRepository extends JpaRepository<QuestionModel, Long> {

    QuestionModel findById(long id);

    void deleteById(long id);
}
