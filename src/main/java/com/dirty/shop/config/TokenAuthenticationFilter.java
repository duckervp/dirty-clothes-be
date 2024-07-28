package com.dirty.shop.config;

import com.dirty.shop.base.WebConstants;
import com.dirty.shop.config.property.AuthProperty;
import com.dirty.shop.enums.Role;
import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.model.User;
import com.dirty.shop.repository.UserRepository;
import com.dirty.shop.utils.AuthTokenUtils;
import com.dirty.shop.utils.AuthenticationUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthProperty authProperty;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("Executing the TokenAuthenticationFilter...");
        String jwt = null;

        try {
            jwt = AuthenticationUtils.getJwtFromRequest(request);
            if (StringUtils.hasText(jwt)) {
                Optional<AuthApiCode> error = AuthTokenUtils.validateToken(
                        jwt, authProperty.getTokenSecret()
                );

                if (error.isPresent()) {
                    AuthenticationUtils.withUnauthorizedResponse(response, error.get());
                    return;
                }

                User user = AuthTokenUtils.loadUserFromJwt(jwt, authProperty.getTokenSecret());
                if (Objects.isNull(user)) {
                    AuthenticationUtils.withUnauthorizedResponse(response, AuthApiCode.INVALID_JWT_TOKEN);
                    return;
                }

                user = userRepository.findByEmail(user.getEmail()).orElseThrow();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context with JWT: [{}]", jwt, ex);
        }

        filterChain.doFilter(request, response);
    }
}
