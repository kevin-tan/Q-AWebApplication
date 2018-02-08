package backend.repository.roles;

import backend.model.roles.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<RoleModel, Long> {
    List<RoleModel> findByUsersId(long id);
}