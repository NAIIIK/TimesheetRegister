package ru.gb.security;

import com.sun.source.tree.BinaryTree;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.entity.Role;
import ru.gb.entity.User;
import ru.gb.repository.RoleRepository;
import ru.gb.repository.UserRepository;
import ru.gb.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Long> userRolesId = userRoleRepository.findUserRolesByUserId(user.getUserId());

        List<String> userRoles = new ArrayList<>();

        for (Long id : userRolesId) {
            userRoles.add(roleRepository.findById(id).get().getName());
        }

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                userRoles.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}
