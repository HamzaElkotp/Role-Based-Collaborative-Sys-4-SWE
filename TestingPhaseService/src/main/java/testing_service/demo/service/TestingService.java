package testing_service.demo.service;

import testing_service.demo.dto.TestCaseDTO;
import testing_service.demo.entity.TestCase;
import testing_service.demo.entity.TestReport;
import testing_service.demo.entity.ReportState;
import testing_service.demo.entity.TestState;
import testing_service.demo.repository.TestCaseRepository;
import testing_service.demo.repository.TestReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestingService {
    private final TestReportRepository reportRepo;
    private final TestCaseRepository caseRepo;

    @Transactional
    public TestReport createReport(Long updateId, String feedback) {
        if (reportRepo.findByUpdateidAndState(updateId, ReportState.DRAFT).isPresent()) {
            throw new RuntimeException("a draft report already exists");
        }
        TestReport report = new TestReport();
        report.setUpdateid(updateId);
        report.setFeedback(feedback);
        report.setState(ReportState.DRAFT);
        return reportRepo.save(report);
    }

    @Transactional
    public List<TestCase> addCases(Long reportId, List<TestCaseDTO> dtos) {
        List<TestCase> cases = dtos.stream().map(dto -> {
            TestCase tc = new TestCase();
            tc.setReportid(reportId);
            tc.setTitle(dto.getTitle());
            tc.setDescription(dto.getDescription());
            tc.setState(TestState.PENDING);
            return tc;
        }).collect(Collectors.toList());
        return caseRepo.saveAll(cases);
    }

    @Transactional
    public TestReport submitReport(Long reportId) {
        TestReport report = reportRepo.findById(reportId).orElseThrow();
        List<TestCase> cases = caseRepo.findByReportid(reportId);
        if (cases.isEmpty()) throw new RuntimeException("add cases first");
        if (cases.stream().anyMatch(c -> c.getState() == TestState.PENDING)) {
            throw new RuntimeException("pending cases exist");
        }
        report.setState(ReportState.SUBMITTED);
        return reportRepo.save(report);
    }
}