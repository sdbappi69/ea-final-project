package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberPlanDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String  email;
    private String  password;
    private List<BadgeDTO> badgeDTOS = new ArrayList<>();
    private List<RoleDTO> roleTypes = new ArrayList<>();
    private List<MembershipDTO> membershipDTOS = new ArrayList<>();
    private List<Long> plansId = new ArrayList<>();
    public MemberPlanDTO(Long id , String firstname, String lastname, String email,String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
