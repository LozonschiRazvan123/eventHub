package Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import Model.Reservation;
public interface ReservationRepository extends JpaRepository<Reservation, Long> 
{
	
}
