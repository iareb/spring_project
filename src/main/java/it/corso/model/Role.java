package it.corso.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruolo")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_G")
	private int id;
	
	@Column(name = "TIPOLOGIA")
	@Enumerated(EnumType.STRING)
	private Tipology tipology;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable
	(
		name = "utente_ruolo", joinColumns = @JoinColumn(name = "FK_R", referencedColumnName = "ID_G"),
		inverseJoinColumns = @JoinColumn(name = "FK_U", referencedColumnName = "ID_U")
	)
	private List<User> users = new ArrayList<>();
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tipology getTipology() {
		return tipology;
	}

	public void setTipology(Tipology tipology) {
		this.tipology = tipology;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
