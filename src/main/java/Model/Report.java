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
public class Report {
	@EqualsAndHashCode.Include
    @NonNull
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    private Event event; 

    @NonNull
    private String generatedBy; 

	@NonNull
	@Temporal(TemporalType.DATE)
    private Date generatedAt;

	@NonNull
    private String format; 
	@NonNull
    private byte[] content;
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	} 
	
	public Report(Long reportId, Event event, String generatedBy, Date generatedAt, String format, byte[] content) {
	    this.reportId = reportId;
	    this.event = event;
	    this.generatedBy = generatedBy;
	    this.generatedAt = generatedAt;
	    this.format = format;
	    this.content = content;
	}
	public Report() {
		super();
	}
	
	
}

