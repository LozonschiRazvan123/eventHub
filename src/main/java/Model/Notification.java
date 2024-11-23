package Model;


import java.util.Date;

import Model.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class Notification {
	@EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne
    private User user; 

    @NonNull
    private String message;
    
    @NonNull
    private Boolean status;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public Notification(Long notificationId, String message, Boolean status, Date sentAt) {
	    this.notificationId = notificationId;
	    this.message = message;
	    this.status = status;
	    this.sentAt = sentAt;
	}

	public Notification() {
		super();
	}
	
}
