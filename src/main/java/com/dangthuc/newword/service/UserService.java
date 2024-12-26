package com.dangthuc.newword.service;

import com.dangthuc.newword.dto.request.UserForm;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.dto.response.UserInfoDto;
import com.dangthuc.newword.entities.Role;
import com.dangthuc.newword.entities.User;
import com.dangthuc.newword.enums.Gender;
import com.dangthuc.newword.repository.RoleRepository;
import com.dangthuc.newword.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service("userDetailsService")
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.checkUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Incorrect account information");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }

    public User checkUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserInfoDto create(UserForm form) {
        try {

            User user = new User(form);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus("01");
            Role role = roleRepository.findById(user.getRole().getId()).orElse(null);
            if (role != null) {
                user.setRole(role);
            }

            return userRepository.save(user).userInfoDto();
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Không tìm thấy role");
        }
    }

    public User fetchUserById(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    public UserInfoDto update(UserForm request) {
        try {
            User currentUser = fetchUserById(request.getId());
            if (currentUser != null) {
                currentUser.setFirstName(request.getFirstName());
                currentUser.setLastName(request.getLastName());
                currentUser.setAddress(request.getAddress());
                currentUser.setPhone(request.getPhone());
                currentUser.setGender("MALE".equals(request.getGender()) ? Gender.MALE :
                        "FEMALE".equals(request.getGender()) ? Gender.FEMALE :
                                Gender.OTHER);
                currentUser.setDob(request.getDob());

                Role role = roleRepository.findById(request.getRole().getId()).orElse(null);
                if (role != null) {
                    currentUser.setRole(role);
                }
                return userRepository.save(currentUser).userInfoDto();
            }
            return null;
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Không tìm thấy role");
        }
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        ResultPaginationDTO paginationDTO = PaginationService.handlePagination(spec, pageable, userRepository);

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) paginationDTO.getResult();

        // Chuyển đổi từ User sang ResUserDTO
        List<UserInfoDto> convertedResults = users.stream()
                .map(User::userInfoDto)
                .toList();

        // Cập nhật lại result sau khi convert
        paginationDTO.setResult(convertedResults);

        return paginationDTO;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
