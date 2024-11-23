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
public class Reservation {
	@EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

    @ManyToOne
    private Event event; 

    @NonNull
    private Integer numberOfTickets;
    @NonNull
    private String paymentStatus; 
    @NonNull
    private String qrCode; 
    
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationDate;
    public Reservation(@NonNull Long reservationId, User user, Event event, 
            @NonNull Integer numberOfTickets, @NonNull String paymentStatus, 
            @NonNull String qrCode, @NonNull Date reservationDate) {
    			super();
    			this.reservationId = reservationId;
    			this.user = user;
    			this.event = event;
    			this.numberOfTickets = numberOfTickets;
    			this.paymentStatus = paymentStatus;
    			this.qrCode = qrCode;
    			this.reservationDate = reservationDate;
    }
	public Reservation() {
		super();
	}
    

}