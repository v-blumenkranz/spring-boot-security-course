package com.example.demo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMINTRAINEE(Sets.newHashSet(ApplicationPermission.STUDENT_READ, ApplicationPermission.COURSE_READ)),
    ADMIN(Sets.newHashSet(
            ApplicationPermission.STUDENT_READ, ApplicationPermission.COURSE_READ,
            ApplicationPermission.COURSE_WRITE, ApplicationPermission.STUDENT_WRITE
    ));

    private final Set<ApplicationPermission> permissions;

    ApplicationUserRole(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
