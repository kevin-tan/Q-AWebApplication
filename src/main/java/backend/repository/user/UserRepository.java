package backend.repository.user;

import backend.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
