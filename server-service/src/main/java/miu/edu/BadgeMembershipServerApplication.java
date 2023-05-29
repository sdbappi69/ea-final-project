package miu.edu;


import lombok.RequiredArgsConstructor;
import miu.edu.domain.Role;
import miu.edu.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Set;


@SpringBootApplication
@RequiredArgsConstructor
public class BadgeMembershipServerApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BadgeMembershipServerApplication.class, args);

    }


    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {

        var role = roleRepository.findAll();
        if (role.size() == 0) {
            Set<Role> roles = Set.of(new Role("ADMIN"),
                    new Role("FACULTY"), new Role("STUDENT"),
                    new Role("STAFF"));
            roleRepository.saveAll(roles);
        }
        System.out.println("Server is running!!!!!!");

    }
}
