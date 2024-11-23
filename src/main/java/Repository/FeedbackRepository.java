package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}