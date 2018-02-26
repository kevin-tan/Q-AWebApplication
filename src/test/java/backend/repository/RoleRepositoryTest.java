package backend.repository;

import backend.model.roles.RoleModel;
import backend.model.user.UserModel;
import backend.repository.roles.RolesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private UserModel user;

    private RoleModel adminRole;
    private RoleModel userRole;

    @Before
    public void setup(){
        //Create roles
        adminRole = new RoleModel("admin");
        userRole = new RoleModel("user");

        //Create users
        user = new UserModel("User One","Pass","User","One","One@Foo.com", "The answer is Foo", "Foo");

        //Set user roles
        user.addRole(userRole);
        userRole.addUser(user);

        //Set admin users
        user.addRole(adminRole);
        adminRole.addUser(user);

        //Save to DB
        testEntityManager.persist(user);
        testEntityManager.persist(adminRole);
        testEntityManager.persist(userRole);
    }

    @Test
    public void whenFindByUserId_andExists_thenReturnUserRoles(){
        List<RoleModel> list = rolesRepository.findByUsersId(user.getId());
        List<RoleModel> userRoles = new ArrayList<>(user.getRoleModel());
        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0).getTitles()).isEqualTo(userRoles.get(0).getTitles());
        assertThat(list.get(1).getTitles()).isEqualTo(userRoles.get(1).getTitles());
    }

    @Test
    public void whenFindByUserId_andNotExists_thenReturnUserRoles(){
        assertThat(rolesRepository.findByUsersId(2L)).isEmpty();
    }

}
