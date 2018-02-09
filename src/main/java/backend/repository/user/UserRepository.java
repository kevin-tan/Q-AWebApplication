package backend.repository.user;

import backend.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    List<UserModel> findByUpVotedVoteModelsId(long id);

    UserModel findByUsername(String username);

    UserModel findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
