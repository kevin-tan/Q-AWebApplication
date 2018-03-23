package backend.repository.roles;

import backend.model.roles.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolesRepository extends JpaRepository<RoleModel, Long> {
    List<RoleModel> findByUsersId(long id);

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE role_model, role_model_users", nativeQuery = true)
    void dropTable();

    @Modifying
    @Transactional
    @Query(value ="CREATE TABLE role_model (id INT AUTO_INCREMENT, title VARCHAR(255))", nativeQuery = true)
    void createRoleModelTable();

    @Modifying
    @Transactional
    @Query(value ="CREATE TABLE role_model_users (roles_id INT, users_id INT)", nativeQuery = true)
    void createRoleModelUsersTable();
}