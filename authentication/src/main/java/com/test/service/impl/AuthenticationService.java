package com.test.service.impl;

import com.test.dto.request.AuthenticationRequest;
import com.test.dto.request.UserRequest;
import com.test.dto.response.AuthenticationResponse;
import com.test.dto.response.UserResponse;
import com.test.entity.Permission;
import com.test.entity.Role;
import com.test.entity.User;
import com.test.repository.PermissionRepository;
import com.test.repository.RoleRepository;
import com.test.repository.UserRepository;
import com.test.security.CustomUserDetail;
import com.test.security.CustomUserDetailService;
import com.test.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.test.constant.CommonConstant.ROLE_DEFAULT;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final PermissionRepository permissionRepository;

    /**
     * Registers a new user with the provided information.
     * Performs username duplication check and encodes the password.
     * Generates a JWT token for the registered user.
     *
     * @param dto UserRequest containing user information
     * @return UserResponse with registered user details and JWT token
     */
    @Transactional
    public UserResponse register(UserRequest dto) {
        this.checkUsernameIfExist(dto.getUsername());
        User user = MapperUtils.toEntity(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Optional<Role> role = roleRepository.findRoleByName(ROLE_DEFAULT);
        Set<Role> roles = new HashSet<>();
        role.ifPresent(roles::add);
        user.setRoles(roles);
        userRepository.save(user);
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                jwtService.generateToken(customUserDetailService.loadUserByUsername(user.getUsername()))
        );
    }

    /**
     * Performs user authentication using the provided credentials.
     * Generates a JWT token for the authenticated user.
     *
     * @param authenticationRequest AuthenticationRequest containing user credentials
     * @return AuthenticationResponse with user ID and JWT token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var token = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        CustomUserDetail customUserDetail = (CustomUserDetail) authenticationManager.authenticate(token).getPrincipal();
        return AuthenticationResponse.builder()
                .id(customUserDetail.getUser().getId())
                .token(jwtService.generateToken(customUserDetail))
                .build();
    }

    /**
     * Authorize the user based on the provided JWT token, URI, and HTTP method.
     *
     * @param token  The JWT token.
     * @param uri    The URI of the request.
     * @param method The HTTP method of the request.
     * @return true if the user is authorized, false otherwise.
     */
    public boolean authorize(String token, String uri, String method) {
        // Extract username from JWT token
        String username = jwtService.extractUsername(token);
        // Load user details by username
        CustomUserDetail customUserDetail = (CustomUserDetail) customUserDetailService.loadUserByUsername(username);
        // Find permissions for the given HTTP method
        Optional<List<Permission>> permissions = permissionRepository.findPermissionByMethod(method);
        // Find permission name for the requested URI
        String permissionName = permissions.flatMap(list -> list.stream()
                        .filter(permission -> uri.contains(permission.getUri()))
                        .map(Permission::getName)
                        .findFirst())
                .orElse(null);
        System.out.println("Permission name" + permissionName);
        // Check if the user has the required permission
        return customUserDetail.getAuthorities().contains(new SimpleGrantedAuthority(permissionName));
    }

    /**
     * Checks if the provided username already exists in the database.
     * Throws DuplicateException if a duplicate username is found.
     *
     * @param username Username to check for duplication
     */
    private void checkUsernameIfExist(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("Username existed!");
        }
    }
}
