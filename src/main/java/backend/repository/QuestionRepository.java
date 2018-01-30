package backend.repository;

import backend.model.qa.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kevin Tan 2018-01-29
 */

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel, Long> {

    QuestionModel findById(long id);

    void deleteById(long id);
}
