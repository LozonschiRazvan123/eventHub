package Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Model.Reservation;
public interface ReservationRepository extends JpaRepository<Reservation, Long> 
{
	@Query("SELECT r FROM Reservation r WHERE r.status = 'activ'")
	Page<Reservation> findActiveReservations(Pageable pageable);

	@Query("SELECT r FROM Reservation r WHERE LOWER(r.event.title) LIKE LOWER(CONCAT('%', :title, '%')) AND r.status = 'activ'")
	Page<Reservation> findByEventTitleAndActiveStatus(@Param("title") String title, Pageable pageable);
}
