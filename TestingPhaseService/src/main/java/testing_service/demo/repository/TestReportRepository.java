package testing_service.demo.repository;

import testing_service.demo.entity.TestReport;
import testing_service.demo.entity.ReportState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TestReportRepository extends JpaRepository<TestReport, Long> {
    Optional<TestReport> findByUpdateidAndState(Long updateid, ReportState state);
}