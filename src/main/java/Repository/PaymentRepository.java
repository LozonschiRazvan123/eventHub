package Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Model.Event;
import Model.Payment;
import Model.Reservation;

public interface PaymentRepository extends JpaRepository<Payment, Long> 
{

	Optional<Payment> findByReservation(Reservation reservation);
	@Query("SELECT DISTINCT r.event.title FROM Reservation r")
	Page<Payment> findDistinctEvents(Pageable pageable);
	Page<Payment> findByReservation_Event_TitleContainingIgnoreCase(String title, Pageable pageable);

}
