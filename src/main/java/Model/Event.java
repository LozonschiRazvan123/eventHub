package Model;
import java.util.Date;

import Model.User;
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
    @Temporal(TemporalType.DATE)
    private Date dateTime;
    @NonNull
    private Integer maxTickets;

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @NonNull
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
                 @NonNull String location, @NonNull Date dateTime, @NonNull Integer maxTickets, 
                 @NonNull Date createdAt, @NonNull Date updatedAt, User organizer) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.maxTickets = maxTickets;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.organizer = organizer;
    }

	public Event() {
		super();
	}
	
}
