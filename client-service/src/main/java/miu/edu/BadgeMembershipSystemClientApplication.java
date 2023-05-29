package miu.edu;
import miu.edu.dto.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class BadgeMembershipSystemClientApplication implements CommandLineRunner {

    private RestTemplate restTemplate = new RestTemplate();
    private String url = "http://localhost:8080";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(BadgeMembershipSystemClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Long checkerId;
        Long planId;
        Long locationId;
        //MemberDTO loginResponse = new MemberDTO(74L, "Mahi", "Abebeb", "mahi@miu.edu", "qwert");
        LoginResponseDTO loginResponseDTO = login().getBody();
        String token = loginResponseDTO.getToken();
        checkerId = loginResponseDTO.getId();
        MembershipDTO membershipDTO = getMembership(checkerId, token);
        if(membershipDTO.getMembershipType().equals(MembershipType.CHECKER)){
            System.out.println(membershipDTO);
            PlanDTO[] plans = membershipDTO.getPlanDTO().toArray(new PlanDTO[0]);
            System.out.println(Arrays.toString(plans));
            for (int i = 0; i < plans.length; i++) {
                System.out.println("[" + i + "] " + plans[i].getName());
            }
            System.out.print("Choose Your Plan: ");
            int index = scanner.nextInt();
            PlanDTO planDTO = plans[index];
            planId = (planDTO.getId());
            locationId = selectLocation(planId, token).getId();
            scan(checkerId, planId, locationId, token);
        }
        else {
            System.out.println("You are not a CHECKER!!");
        }

    }
    private ResponseEntity<LoginResponseDTO> login(){
        System.out.println("= = = = = = = = = =  = = = = = = = = = = = = = = = = = =  = = = = = = = = =");
        System.out.println("=                               WELCOME TO                                =");
        System.out.println("=                  BADGE AND MEMBERSHIP MANAGEMENT SYSTEM                 =");
        System.out.println("= = = = = = = = = = = = = =   SIGN IN PLEASE  = = = = = = = = = = = = = = =");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        LoginDTO loginDTO = new LoginDTO(email,password);
        ResponseEntity<LoginResponseDTO> response = restTemplate.postForEntity(url+"/login/authenticate", loginDTO, LoginResponseDTO.class);
        return response;
    }
    private MembershipDTO getMembership(long memberId, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        System.out.println(" = = = = = = = = = Memberships = = = = = = = = = =  ");
        System.out.println(memberId);;
        MembershipDTO[] memberships = restTemplate.exchange(url + "/members/" + memberId + "/memberships", HttpMethod.GET, entity, MembershipDTO[].class, memberId).getBody();

        for (int i = 0; i < memberships.length; i++) {
            System.out.println("[" + i + "] " + memberships[i].getMembershipType());
        }
        System.out.print("Choose Your Membership: ");
        int index = scanner.nextInt();
        System.out.println(memberships[index].getMembershipType());
        return memberships[index];
    }
    private LocationDTO selectLocation(long planId, String token) {
        System.out.println("Your Locations are: ");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        LocationDTO[] locations = restTemplate.exchange(url+"/plans/"+planId+"/locations", HttpMethod.GET, entity, LocationDTO[].class).getBody();
        for (int i = 0; i < locations.length; i++) {
            System.out.println("[" + i + "] " + locations[i].getName());
        }
        System.out.print("Choose Your Location: ");
        int index = scanner.nextInt();
        return locations[index];
    }

    private void scan(long checkerId, long planId, long locationId, String token) {
        while (true) {
            System.out.print("Scan a member's badge (Badge ID): ");
            if (scanner.hasNextLong()) {
                Long badgeId = scanner.nextLong();
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
                BadgeDTO badgeDTO = restTemplate.exchange(url+"/badges/"+badgeId, HttpMethod.GET, entity, BadgeDTO.class, badgeId).getBody();
                Long memberId = badgeDTO.getMemberDTO().getId();
                ScanDTO scanDTO = new ScanDTO(checkerId, planId, locationId, memberId);
                HttpEntity<ScanDTO> request = new HttpEntity<>(scanDTO, headers);
                System.out.println(request);
                ResponseEntity<TransactionDTO> response = restTemplate.exchange(url+"/scan", HttpMethod.POST, request, TransactionDTO.class);
                System.out.println(response.getBody().getStatus());
            } else {
                break;
            }
        }
    }

}
