package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {}
