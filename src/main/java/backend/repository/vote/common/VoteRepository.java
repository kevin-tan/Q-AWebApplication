package backend.repository.vote.common;

import backend.model.vote.common.VoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Kevin Tan 2018-01-30
 */

@NoRepositoryBean
public interface VoteRepository<T extends VoteModel> extends JpaRepository<T, Long> {

    T findById(long id);

}
