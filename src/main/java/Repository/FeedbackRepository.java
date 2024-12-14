package Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	Page<Feedback> findByEventEventId(Long eventId, Pageable pageable);


}