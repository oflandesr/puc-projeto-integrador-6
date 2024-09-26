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
@Table(name = "User")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "First_Name")
	private String firstName;

	@Column(name = "Last_Name")
	private String lastName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Login_Id", referencedColumnName = "Id")
	private LoginModel login;

    // Método para retornar um objeto StockModel mock
    public static UserModel getMockUser() {
    	UserModel userModel = new UserModel();
    	userModel.setId(000000);
    	userModel.setFirstName("Tester");
    	userModel.setLastName("Pucc");
    	userModel.setLogin(null);
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

	public LoginModel getLogin() {
		return login;
	}

	public void setLogin(LoginModel login) {
		this.login = login;
	}

}
