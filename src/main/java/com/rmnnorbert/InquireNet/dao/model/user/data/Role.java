package com.rmnnorbert.InquireNet.dao.model.user.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static com.rmnnorbert.InquireNet.dao.model.user.data.Permission.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public enum Role {
    USER(Set.of(
            ANSWER_READ,
            ANSWER_CREATE,
            ANSWER_UPDATE,
            ANSWER_DELETE,
            QUESTION_READ,
            QUESTION_CREATE,
            QUESTION_UPDATE,
            QUESTION_DELETE,
            USER_READ,
            USER_UPDATE,
            USER_CREATE,
            USER_DELETE,
            REPLY_READ,
            REPLY_UPDATE,
            REPLY_CREATE,
            REPLY_DELETE
    )),
    EMPLOYEE(Arrays.stream(Permission.values()).collect(Collectors.toSet()));
    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
