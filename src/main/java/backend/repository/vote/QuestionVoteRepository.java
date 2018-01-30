package backend.repository.vote;

import backend.model.vote.QuestionVoteModel;
import backend.repository.vote.common.VoteRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kevin Tan 2018-01-30
 */

@Transactional
public interface QuestionVoteRepository extends VoteRepository<QuestionVoteModel> {

}
