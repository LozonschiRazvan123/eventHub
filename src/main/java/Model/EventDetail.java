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
	public Long getEventDetailId() {
		return eventDetailId;
	}
	public void setEventDetailId(Long eventDetailId) {
		this.eventDetailId = eventDetailId;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Boolean getAvailability() {
		return availability;
	}
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
    
}