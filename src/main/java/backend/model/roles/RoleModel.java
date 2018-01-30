package backend.model.roles;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import backend.model.roles.common.RoleTitle;

/**
 * Created by Luca Fiorilli 2018-01-30
 */

@Entity
public class RoleModel {
	
	@Id
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	
	@ManyToMany
	private Set<String> titles;
	
	public RoleModel(Long id, Set<String> title){
		this.id = id;
		this.titles = title;
	}
	
	public RoleModel(Long id, RoleTitle title){
		this.id = id;
		this.titles = new HashSet<String>();
		titles.add(title.toString());
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public void setTitle(RoleTitle title){
		if (!titles.contains(title.toString())){
			titles.add(title.toString());
		}
	}
	
	public Long getId(){
		return this.id;
	}
	
	public Set<String> getTitles(){
		return titles;
	}
}
