package Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Model.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@Table(name = "app_user")
public class User {
	
	@EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
	
	@NonNull
    private String name;
	@NonNull
    private String email;
	@NonNull
    private String passwordHash;
	@NonNull
    private String role; 
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Reservation> reservations;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	public User(@NonNull Long userId, @NonNull String email, @NonNull String passwordHash, @NonNull String name,
			@NonNull String role) {
		super();
		this.userId = userId;
		this.email = email;
		this.passwordHash = passwordHash;
		this.name = name;
		this.role = role;
	}
	public User() {
		super();
	}
    
}
