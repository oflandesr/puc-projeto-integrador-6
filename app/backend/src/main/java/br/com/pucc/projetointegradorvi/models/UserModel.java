package br.com.pucc.projetointegradorvi.models;

//@Entity
//@Table(name = "users")
public class UserModel {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@Column(nullable = false, unique = true)
	private String uid;

	//@Column(nullable = false)
	private String email;

	//@Column(nullable = false)
	private boolean emailVerified;

	private String displayName;
	private String photoUrl;

	// Construtores
	public UserModel() {
	}

	public UserModel(String uid, String email, boolean emailVerified, String displayName, String photoUrl) {
		this.uid = uid;
		this.email = email;
		this.emailVerified = emailVerified;
		this.displayName = displayName;
		this.photoUrl = photoUrl;
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Override
	public String toString() {
		return "UserModel{" + "id=" + id + ", uid='" + uid + '\'' + ", email='" + email + '\'' + ", emailVerified="
				+ emailVerified + ", displayName='" + displayName + '\'' + ", photoUrl='" + photoUrl + '\'' + '}';
	}

	// Método estático para mock
	public static UserModel getMockUser() {
		return new UserModel("mockUid12345", // uid mock
				"mockuser@example.com", // email mock
				true, // email verificado
				"Mock User", // nome mock
				"https://example.com/mockphoto.jpg" // URL da foto mock
		);
	}
}
