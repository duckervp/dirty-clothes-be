package com.dirty.shop.utils;

import com.dirty.shop.base.ApiCode;
import com.dirty.shop.base.Response;
import com.dirty.shop.enums.Role;
import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;

@UtilityClass
@Slf4j
public class AuthUtils {

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static Optional<Role> currentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Role> role = Optional.empty();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            role = Optional.of(user.getRole());
        }
        return role;
    }

    public static Long currentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User userPrincipal = (User) authentication.getPrincipal();
            return userPrincipal.getId();
        } catch (Exception e) {
            log.error("exception ", e);
            throw new ApiException(AuthApiCode.USER_ID_NOT_FOUND_IN_CONTEXT_HOLDER);
        }
    }

    public static void withUnauthorizedResponse(HttpServletResponse response, ApiCode code) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(SerializeUtils.toJson(Response.with(code)));
    }

    public static void withPermissionDeniedResponse(HttpServletResponse response, ApiCode code) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().println(SerializeUtils.toJson(Response.with(code)));
    }

}
