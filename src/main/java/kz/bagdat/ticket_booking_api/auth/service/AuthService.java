package kz.bagdat.ticket_booking_api.auth.service;

import kz.bagdat.ticket_booking_api.auth.dto.RegisterRequest;
import kz.bagdat.ticket_booking_api.user.entity.RoleEntity;
import kz.bagdat.ticket_booking_api.user.entity.UserEntity;
import kz.bagdat.ticket_booking_api.user.repository.RoleRepository;
import kz.bagdat.ticket_booking_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User with this email already exists");
        }

        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.getRoles().add(userRole);

        userRepository.save(user);
    }
}
