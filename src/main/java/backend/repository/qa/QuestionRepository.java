package backend.repository.qa;

import backend.model.qa.QuestionModel;
import backend.repository.qa.common.ForumPostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface QuestionRepository extends ForumPostRepository<QuestionModel> {
    List<QuestionModel> findByUserQuestionId(long id);

    List<QuestionModel> findByQuestionTitleContains(String titleToken);

    List<QuestionModel> findByQuestionCategories(String category);
}
