package backend.model.roles;

import backend.model.user.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luca Fiorilli 2018-01-30
 */

@Entity
public class RoleModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;

    @ManyToMany
    @JsonIgnore
    private Set<UserModel> users = new HashSet<>();

    public RoleModel() {

    }

    public RoleModel(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitles() {
        return title;
    }

    public void addUser(UserModel userModel){
        users.add(userModel);
    }

    public void removeUser(UserModel userModel){
        users.remove(userModel);
    }
}