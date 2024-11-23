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
public class Feedback {
	@EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @ManyToOne
    private Event event; 

    @ManyToOne
    private User user; 
    
    @OneToOne
    private Payment payment;

    @NonNull
    private Integer rating; 
    @NonNull
    private String comments;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedAt;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Feedback(Long feedbackId, Integer rating, String comments) {
	    this.feedbackId = feedbackId;
	    this.rating = rating;
	    this.comments = comments;
	}

	public Feedback() {
		super();
	}
	
}
