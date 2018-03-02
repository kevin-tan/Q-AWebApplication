package backend.repository.qa;

import backend.model.qa.AnswerModel;
import backend.repository.qa.common.ForumPostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AnswerRepository extends ForumPostRepository<AnswerModel> {
    List<AnswerModel> findByQuestionId(long questionId);

    List<AnswerModel> findByUserAnswerId(long userId);
}
