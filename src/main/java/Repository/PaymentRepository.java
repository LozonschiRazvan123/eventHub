package Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import Model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> 
{
	
}
