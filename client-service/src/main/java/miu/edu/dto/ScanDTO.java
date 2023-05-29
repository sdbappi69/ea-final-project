package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanDTO {
    Long checker_id;
    Long plan_id;
    Long location_id;
    Long member_id;
}
