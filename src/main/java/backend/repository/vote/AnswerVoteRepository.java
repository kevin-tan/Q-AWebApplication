package backend.repository.vote;

import backend.model.vote.AnswerVoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kevin Tan 2018-01-30
 */
public interface AnswerVoteRepository extends JpaRepository<AnswerVoteModel, Long> {
    AnswerVoteModel findByAnswerId(long answerId);
}
