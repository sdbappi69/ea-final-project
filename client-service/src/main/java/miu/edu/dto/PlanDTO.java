package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlanDTO {
    private long id;
    private String name;
    private String description;

    private List<LocationDTO> locations;
}
