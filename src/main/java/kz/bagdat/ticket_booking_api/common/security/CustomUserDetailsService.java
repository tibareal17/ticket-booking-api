package kz.bagdat.ticket_booking_api.common.security;

import kz.bagdat.ticket_booking_api.user.entity.RoleEntity;
import kz.bagdat.ticket_booking_api.user.entity.UserEntity;
import kz.bagdat.ticket_booking_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var authorities = user.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        return new User(
                user.getEmail(),
                user.getPasswordHash(),
                authorities
        );
    }
}