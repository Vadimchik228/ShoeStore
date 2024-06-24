package com.vasche.shoestore.web.security.expression;

import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.service.UserService;
import com.vasche.shoestore.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean hasAdminRights() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    public boolean canAccessUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    public boolean canAccessCartItem(Long cartItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isCartItemOwner(userId, cartItemId) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    public boolean canAccessOrderItem(Long orderItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isOrderItemOwner(userId, orderItemId) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    public boolean canAccessOrder(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isOrderOwner(userId, orderId) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

}
