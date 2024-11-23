package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Model.EventDetail;

public interface EventDetailRepository extends JpaRepository<EventDetail, Long> 
{
	
}