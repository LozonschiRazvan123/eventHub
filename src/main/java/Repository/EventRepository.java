package Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import Model.Event;

public interface EventRepository extends JpaRepository<Event, Long> 
{
	
}
