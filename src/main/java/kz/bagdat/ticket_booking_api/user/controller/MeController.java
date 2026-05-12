package kz.bagdat.ticket_booking_api.user.controller;

import kz.bagdat.ticket_booking_api.user.dto.UserProfileResponse;
import kz.bagdat.ticket_booking_api.user.entity.UserEntity;
import kz.bagdat.ticket_booking_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public UserProfileResponse me(Authentication authentication) {
        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}