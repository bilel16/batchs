package com.bna.batches.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Stub UserDetailsService — required by Spring Security DaoAuthenticationProvider.
 * Actual authentication is delegated to the external auth service (urlAuth).
 * Per-request role validation is done in JwtRequestFilter via urlroles.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("Direct DB lookup not supported — use external auth service");
    }
}
