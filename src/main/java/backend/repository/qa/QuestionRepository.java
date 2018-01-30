package backend.repository.qa;

import backend.model.qa.QuestionModel;
import backend.repository.qa.common.ForumPostRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kevin Tan 2018-01-29
 */

@Transactional
public interface QuestionRepository extends ForumPostRepository<QuestionModel> {
}
