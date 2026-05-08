package testing_service.demo.entity;

import testing_service.demo.entity.ReportState;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "test_reports")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_id")
    private Long updateid;

    private String feedback;

    @Enumerated(EnumType.STRING)
    private ReportState state;

    @OneToMany(mappedBy = "reportid", cascade = CascadeType.ALL)
    private List<TestCase> testcases;
}