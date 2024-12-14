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

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}
	
}
