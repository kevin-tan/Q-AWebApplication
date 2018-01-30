package backend.repository.vote;

import backend.model.vote.AnswerVoteModel;
import backend.repository.vote.common.VoteRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kevin Tan 2018-01-30
 */

@Transactional
public interface AnswerVoteRepository extends VoteRepository<AnswerVoteModel> {

}
