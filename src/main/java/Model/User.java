package Model;

import java.util.List;

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
    private List<Reservation> reservations;
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
