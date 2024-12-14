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
    @Column(nullable = false)
    private String status; 
    
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationDate;
    public Reservation(@NonNull Long reservationId, User user, Event event, 
            @NonNull Integer numberOfTickets, @NonNull String paymentStatus, 
            @NonNull String qrCode, @NonNull String status, @NonNull Date reservationDate) {
    			super();
    			this.reservationId = reservationId;
    			this.user = user;
    			this.event = event;
    			this.numberOfTickets = numberOfTickets;
    			this.paymentStatus = paymentStatus;
    			this.qrCode = qrCode;
    			this.status = status;
    			this.reservationDate = reservationDate;
    }
	public Reservation() {
		super();
	}
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}
	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
	
}