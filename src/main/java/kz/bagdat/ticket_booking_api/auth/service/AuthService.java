package kz.bagdat.ticket_booking_api.auth.service;

import kz.bagdat.ticket_booking_api.auth.dto.LoginRequest;
import kz.bagdat.ticket_booking_api.auth.dto.RegisterRequest;
import kz.bagdat.ticket_booking_api.common.exception.InvalidCredentialsException;
import kz.bagdat.ticket_booking_api.common.exception.RoleNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.UserAlreadyExistsException;
import kz.bagdat.ticket_booking_api.user.entity.RoleEntity;
import kz.bagdat.ticket_booking_api.user.entity.UserEntity;
import kz.bagdat.ticket_booking_api.user.repository.RoleRepository;
import kz.bagdat.ticket_booking_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Role USER not found"));

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.getRoles().add(userRole);

        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public void login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Email or password is incorrect"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Email or password is incorrect");
        }
    }
}
