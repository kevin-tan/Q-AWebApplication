package backend.repository.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.model.roles.RoleModel;

/**
 * Created by Luca Fiorilli 2018-01-30
 */

public interface RolesRepository extends JpaRepository<RoleModel, Long> {
}