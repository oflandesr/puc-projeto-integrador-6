package br.com.pucc.projetointegradorvi.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "login", referencedColumnName = "login", unique = true)
	private LoginModel access;

	public UserModel() {}
	
	public UserModel(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UserModel(String firstName, String lastName, LoginModel login) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.access = login;
	}
	
    // Método para retornar um objeto StockModel mock
    public static UserModel getMockUser() {
    	UserModel userModel = new UserModel();
    	userModel.setId(000000);
    	userModel.setFirstName("Tester");
    	userModel.setLastName("Pucc");
    	userModel.setAccess(null);
        return userModel;
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LoginModel getAccess() {
		return access;
	}

	public void setAccess(LoginModel login) {
		this.access = login;
	}

}
