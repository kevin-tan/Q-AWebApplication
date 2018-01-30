package backend.repository.qa.common;

import backend.model.qa.common.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Kevin Tan 2018-01-30
 */

@NoRepositoryBean
public interface ForumPostRepository<T extends ForumPost> extends JpaRepository<T, Long>{
    T findById(long id);

    void deleteById(long id);
}
