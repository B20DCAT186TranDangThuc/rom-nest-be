package com.dangthuc.newword.config;

import com.dangthuc.newword.entities.Permission;
import com.dangthuc.newword.entities.Role;
import com.dangthuc.newword.entities.User;
import com.dangthuc.newword.enums.Gender;
import com.dangthuc.newword.repository.PermissionRepository;
import com.dangthuc.newword.repository.RoleRepository;
import com.dangthuc.newword.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countPermissions = this.permissionRepository.count();
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();
        if (countPermissions == 0) {
            ArrayList<Permission> arr = new ArrayList<>();
            arr.add(new Permission("Create a permission", "/api/v1/permissions", "POST", "PERMISSIONS"));
            arr.add(new Permission("Update a permission", "/api/v1/permissions", "PUT", "PERMISSIONS"));
            arr.add(new Permission("Delete a permission", "/api/v1/permissions/{id}", "DELETE", "PERMISSIONS"));
            arr.add(new Permission("Get a permission by id", "/api/v1/permissions/{id}", "GET", "PERMISSIONS"));
            arr.add(new Permission("Get permissions with pagination", "/api/v1/permissions", "GET", "PERMISSIONS"));

            arr.add(new Permission("Create a role", "/api/v1/roles", "POST", "ROLES"));
            arr.add(new Permission("Update a role", "/api/v1/roles", "PUT", "ROLES"));
            arr.add(new Permission("Delete a role", "/api/v1/roles/{id}", "DELETE", "ROLES"));
            arr.add(new Permission("Get a role by id", "/api/v1/roles/{id}", "GET", "ROLES"));
            arr.add(new Permission("Get roles with pagination", "/api/v1/roles", "GET", "ROLES"));

            arr.add(new Permission("Create a user", "/api/v1/users", "POST", "USERS"));
            arr.add(new Permission("Update a user", "/api/v1/users", "PUT", "USERS"));
            arr.add(new Permission("Delete a user", "/api/v1/users/{id}", "DELETE", "USERS"));
            arr.add(new Permission("Get a user by id", "/api/v1/users/{id}", "GET", "USERS"));
            arr.add(new Permission("Get users with pagination", "/api/v1/users", "GET", "USERS"));

            arr.add(new Permission("Create a room", "/api/v1/rooms", "POST", "ROOMS"));
            arr.add(new Permission("Update a room", "/api/v1/rooms/{id}", "PUT", "ROOMS"));
            arr.add(new Permission("Delete a room", "/api/v1/rooms/{id}", "DELETE", "ROOMS"));
            arr.add(new Permission("Get a room by id", "/api/v1/rooms/{id}", "GET", "ROOMS"));
            arr.add(new Permission("Get rooms with pagination", "/api/v1/rooms", "GET", "ROOMS"));

            arr.add(new Permission("Create a service", "/api/v1/services", "POST", "SERVICES"));
            arr.add(new Permission("Update a service", "/api/v1/services/{id}", "PUT", "SERVICES"));
            arr.add(new Permission("Delete a service", "/api/v1/services/{id}", "DELETE", "SERVICES"));
            arr.add(new Permission("Get a service by id", "/api/v1/services/{id}", "GET", "SERVICES"));
            arr.add(new Permission("Get services with pagination", "/api/v1/services", "GET", "SERVICES"));

            arr.add(new Permission("Create a contact", "/api/v1/contacts", "POST", "CONTACTS"));
            arr.add(new Permission("Update a contact", "/api/v1/contacts/{id}", "PUT", "CONTACTS"));
            arr.add(new Permission("Delete a contact", "/api/v1/contacts/{id}", "DELETE", "CONTACTS"));
            arr.add(new Permission("Get a contact by id", "/api/v1/contacts/{id}", "GET", "CONTACTS"));
            arr.add(new Permission("Get contacts with pagination", "/api/v1/contacts", "GET", "CONTACTS"));

            arr.add(new Permission("Create an invoice", "/api/v1/invoices", "POST", "INVOICES"));
            arr.add(new Permission("Update an invoice", "/api/v1/invoices/{id}", "PUT", "INVOICES"));
            arr.add(new Permission("Delete an invoice", "/api/v1/invoices/{id}", "DELETE", "INVOICES"));
            arr.add(new Permission("Get an invoice by id", "/api/v1/invoices/{id}", "GET", "INVOICES"));
            arr.add(new Permission("Get invoices with pagination", "/api/v1/invoices", "GET", "INVOICES"));
            this.permissionRepository.saveAll(arr);
        }
        if (countRoles == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();
            Role adminRole = new Role();
            adminRole.setName("SUPER_ADMIN");
            adminRole.setDescription("Admin thì full permissions");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);
            this.roleRepository.save(adminRole);
        }
        if (countUsers == 0) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@admin.com");
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Admin");
            adminUser.setGender(Gender.MALE);
            adminUser.setPhone("0915xxxxxxxx");
            adminUser.setAddress("Ha Noi");
            adminUser.setDob(LocalDate.now());
            adminUser.setStatus("01");
            adminUser.setPassword(this.passwordEncoder.encode("admin"));
            Role adminRole = this.roleRepository.findByName("SUPER_ADMIN");
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }
            this.userRepository.save(adminUser);
        }
        if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");
    }
}
