package Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import Model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {}