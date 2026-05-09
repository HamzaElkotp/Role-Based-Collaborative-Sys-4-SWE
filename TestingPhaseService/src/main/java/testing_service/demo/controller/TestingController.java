package testing_service.demo.controller;

import testing_service.demo.dto.TestCaseDTO;
import testing_service.demo.entity.TestCase;
import testing_service.demo.entity.TestReport;
import testing_service.demo.entity.TestState;
import testing_service.demo.repository.TestCaseRepository;
import testing_service.demo.repository.TestReportRepository;
import testing_service.demo.service.TestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/testing/reports")
@RequiredArgsConstructor
@CrossOrigin
public class TestingController {
    private final TestingService testingService;
    private final TestReportRepository reportRepo;
    private final TestCaseRepository caseRepo;

    @PostMapping("/create/{update_id}")
    public ResponseEntity<?> create(@PathVariable Long update_id, @RequestParam(required = false) String feedback) {
        try {
            return ResponseEntity.ok(testingService.createReport(update_id, feedback));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{report_id}/cases")
    public ResponseEntity<?> add(@PathVariable Long report_id, @RequestBody List<TestCaseDTO> dtos) {
        return ResponseEntity.ok(testingService.addCases(report_id, dtos));
    }

    @GetMapping("/{report_id}/cases")
    public ResponseEntity<List<TestCase>> getCases(@PathVariable Long report_id) {
        return ResponseEntity.ok(caseRepo.findByReportid(report_id));
    }

    @PatchMapping("/cases/{case_id}")
    public ResponseEntity<TestCase> updateState(@PathVariable Long case_id, @RequestParam("status") TestState status) {
        TestCase tc = caseRepo.findById(case_id).orElseThrow();
        tc.setState(status);
        return ResponseEntity.ok(caseRepo.save(tc));
    }

    @PatchMapping("/{report_id}/submit")
    public ResponseEntity<?> submit(@PathVariable Long report_id) {
        try {
            return ResponseEntity.ok(testingService.submitReport(report_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{report_id}")
    public ResponseEntity<TestReport> getReport(@PathVariable Long report_id) {
        return ResponseEntity.ok(reportRepo.findById(report_id).orElseThrow());
    }
}