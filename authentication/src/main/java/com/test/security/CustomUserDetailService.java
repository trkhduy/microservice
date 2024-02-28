package com.test.security;

import com.test.entity.Role;
import com.test.entity.User;
import com.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.test.constant.CommonConstant.AUTHORIZATION_PREFIX;
/**
 * CustomUserDetailService class implements the UserDetailsService interface to provide custom user details retrieval logic.
 * It is responsible for loading user details from the UserRepository based on the provided username.
 */

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user details by username, including roles and permissions.
     *
     * @param username The username of the user.
     * @return A CustomUserDetail object containing user details and granted authorities.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }

    private CustomUserDetail getUser(String username) {

        Optional<User> userOptional = userRepository.findUserByUsername(username);
        // Throw an exception if the user is not found
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Can't find user!");
        }

        // Collection to store the granted authorities (roles and permissions)
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        // Retrieve the roles associated with the user
        Set<Role> roles = userOptional.get().getRoles();
        // Set to keep track of added permissions to avoid duplicates

        for (Role role : roles) {
            // Add role as a granted authority with a prefixed string
            grantedAuthoritySet.add(new SimpleGrantedAuthority(AUTHORIZATION_PREFIX + role.getName()));
            // Iterate through permissions associated with the role
            role.getPermissions().forEach(permission -> {
                String permissionName = permission.getName();
                // Add permission as a granted authority
                grantedAuthoritySet.add(new SimpleGrantedAuthority(permissionName));
            });
        }
        // Create and return a CustomUserDetail object with user details and granted authorities
        return new CustomUserDetail(userOptional.get(), grantedAuthoritySet);

    }


}
