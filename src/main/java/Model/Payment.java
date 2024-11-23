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
public class Payment {
	
	@EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    private Reservation reservation; 

    @ManyToOne
    private User user; 

    @NonNull
    private Double amount;
    @NonNull
    private String paymentMethod;
    public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NonNull
    private String status; 
    
	@NonNull
	@Temporal(TemporalType.DATE)
    private Date paymentDate;
	
	public Payment(@NonNull Long paymentId, @NonNull Double amount, 
            @NonNull String paymentMethod, @NonNull String status, 
            @NonNull Date paymentDate) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.paymentDate = paymentDate;
	}

	public Payment() {
		super();
	}
	

}