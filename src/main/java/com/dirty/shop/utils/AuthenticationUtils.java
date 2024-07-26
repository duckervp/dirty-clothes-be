package com.dirty.shop.utils;

import com.dirty.shop.base.ApiCode;
import com.dirty.shop.base.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@UtilityClass
@Slf4j
public class AuthenticationUtils {

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

//    public static Long currentMemberId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Long currentMemberId = null;
//        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal memberPrincipal) {
//            currentMemberId = memberPrincipal.getId();
//        }
//        return currentMemberId;
//    }
//
//    public static Optional<Role> currentRole() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Optional<Role> role = Optional.empty();
//        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal memberPrincipal) {
//            role = Optional.of(memberPrincipal.getRole());
//        }
//        return role;
//    }
//
//    public static Long getIdFromAuthentication(Authentication authentication) {
//        Long id = null;
//        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
//            id = userPrincipal.getId();
//        }
//        return id;
//    }
//
//    public static Long currentUserId() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//            return userPrincipal.getId();
//        } catch (Exception e) {
//            log.error("exception ", e);
//            throw new ApiException(AuthenticationApiCode.USER_ID_NOT_FOUND_IN_CONTEXT_HOLDER);
//        }
//    }
//
//    public static Long customerId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Long customerId = null;
//        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
//            customerId = userPrincipal.getId();
//        }
//        return customerId;
//    }
//
//    public static String currentUsername() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentUsername = null;
//            if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal memberPrincipal) {
//                currentUsername = memberPrincipal.getUsername();
//            }
//            return currentUsername;
//        } catch (Exception e) {
//            LogUtils.error("Cannot extract user information from SecurityContextHolder");
//            return null;
//        }
//    }
//
//    public static String currentUserUuid() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentUserUuid = null;
//            if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
//                currentUserUuid = userPrincipal.getUuid().toString();
//            }
//            return currentUserUuid;
//        } catch (Exception e) {
//            LogUtils.error("Cannot extract user information from SecurityContextHolder");
//            return null;
//        }
//    }
//
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
