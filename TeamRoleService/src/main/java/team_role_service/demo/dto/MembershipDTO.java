package team_role_service.demo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MembershipDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long project_id;
    private Long user_id;
    private Long role_id;
    private LocalDateTime joined_at;
}