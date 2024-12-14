package SeedData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Repository.*;
import Model.*;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            EventRepository eventRepository,
            EventDetailRepository eventDetailRepository,
            FeedbackRepository feedbackRepository,
            NotificationRepository notificationRepository,
            PaymentRepository paymentRepository,
            ReportRepository reportRepository,
            ReservationRepository reservationRepository) {

        return args -> {
        	System.out.println("Initializing database with seed data...");
        	User user1 = new User(1L,  "john.doe@example.com", "hashedPassword1", "John Doe", "USER");
            User user2 = new User(2L, "jane.smith@example.com", "hashedPassword2", "Jane Smith", "ADMIN");
            userRepository.saveAll(Arrays.asList(user1, user2));

            for (int i = 1; i <= 10; i++) {
                Event event = new Event((long) i, "Event " + i, "Description for event " + i, "Location " + i, 500 + i * 10, new Date(), new Date(), user2);
                event.setOrganizer(user1);
                eventRepository.save(event);

                // Event Details
                for (int j = 1; j <= 5; j++) {
                    EventDetail eventDetail = new EventDetail(null,"Seat " + i + "-" + j, "Section " + i, 50.0 + j * 5, true);
                    eventDetail.setEvent(event);
                    eventDetailRepository.save(eventDetail);
                }

                // Reservations
                Reservation reservation = new Reservation(null, user1, event, i, "PAID", "QR" + i, (i % 2 != 0) ? "activ" : "inactiv", new Date());
                System.out.println("Creating reservation with status: " + ((i % 2 != 0) ? "activ" : "inactiv"));

                reservationRepository.save(reservation);

                // Payments
                Payment payment = new Payment(
                        null,
                        100.0 + i * 10,
                        "CREDIT_CARD",
                        "COMPLETED",
                        new Date()
                );
                payment.setReservation(reservation);
                payment.setUser(user1);
                paymentRepository.save(payment);

                // Feedback
                Feedback feedback = new Feedback(
                        null,
                        i % 5 + 1,
                        "Feedback for event " + i
                );
                feedback.setEvent(event);
                feedback.setUser(user1);
                feedback.setPayment(payment);
                feedbackRepository.save(feedback);

                // Notifications
                Notification notification = new Notification(
                        null,
                        "Notification for event " + i,
                        true,
                        new Date()
                );
                notification.setUser(user1);
                notificationRepository.save(notification);

                // Reports
                Report report = new Report(
                        null,
                        null,
                        "System",
                        new Date(),
                        "PDF",
                        ("Report content for event " + i).getBytes()
                );
                report.setEvent(event);
                reportRepository.save(report);
            }
            
        };
         
    }
}
