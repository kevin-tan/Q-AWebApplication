package backend.repository.vote;

import backend.model.vote.QuestionVoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kevin Tan 2018-01-30
 */
public interface QuestionVoteRepository extends JpaRepository<QuestionVoteModel, Long> {
    QuestionVoteModel findByQuestionId(long questionId);
}
