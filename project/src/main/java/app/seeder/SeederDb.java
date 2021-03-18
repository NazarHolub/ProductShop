package app.seeder;

import app.constants.Roles;
import app.entities.Role;
import app.entities.User;
import app.repositories.IRoleRepository;
import app.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SeederDb {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SeederDb(IUserRepository userRepository,
                    IRoleRepository roleRepository,
                    PasswordEncoder passwordEncoder/*why here is red, all good... yes?*/) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void SeedAllTabels() {
        SeedRole();
        SeedUser();
    }
    public void SeedRole() {
        if(roleRepository.count() == 0) {
            Role role = new Role();
            role.setName(Roles.Admin);
            roleRepository.save(role);
            role = new Role();
            role.setName(Roles.User);
            roleRepository.save(role);
        }
    }

    public void SeedUser() {
        if(userRepository.count() == 0) {
            User user = new User();
            user.setName("User");
            user.setEmail("smth@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Arrays.asList(
                    roleRepository.findByName(Roles.Admin)));
            userRepository.save(user);
        }
    }
}
