package Model;
import Model.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class EventDetail {
	@EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventDetailId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @NonNull
    private String seatNumber;
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	@NonNull
    private String section;
    @NonNull
    private Double price;
    @NonNull
    private Boolean availability;
    public EventDetail(@NonNull Long eventDetailId, @NonNull String seatNumber, 
            @NonNull String section, @NonNull Double price, @NonNull Boolean availability) {
    			super();
    			this.eventDetailId = eventDetailId;
    			this.seatNumber = seatNumber;
    			this.section = section;
    			this.price = price;
    			this.availability = availability;
    }
	public EventDetail() {
		super();
	}
    
}