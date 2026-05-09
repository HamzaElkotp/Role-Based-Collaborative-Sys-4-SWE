package testing_service.demo.entity;

import testing_service.demo.entity.TestState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "test_cases")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private Long reportid;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TestState state;
}