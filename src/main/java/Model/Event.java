package Model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class Event {
    @EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private String location;
    @NonNull
    private Integer maxTickets;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @ManyToOne
    private User organizer;

    public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	public Event(@NonNull Long eventId, @NonNull String title, @NonNull String description, 
                 @NonNull String location, @NonNull Integer maxTickets, 
                 @NonNull Date createdAt, @NonNull Date updatedAt, User organizer) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.maxTickets = maxTickets;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.organizer = organizer;
    }

	public Event() {
		super();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getMaxTickets() {
		return maxTickets;
	}

	public void setMaxTickets(Integer maxTickets) {
		this.maxTickets = maxTickets;
	}
	
	
}
