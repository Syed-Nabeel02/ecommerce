package com.ecommerce.project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.project.DAO.RoleDAO;
import com.ecommerce.project.DAO.UserDAO;
import com.ecommerce.project.DTO.AddressDto;
import com.ecommerce.project.DTO.AuthenticationResult;
import com.ecommerce.project.DTO.PaymentCardDTO;
import com.ecommerce.project.DTO.UserDTO;
import com.ecommerce.project.DTO.UserResponse;
import com.ecommerce.project.errorHandler.ResourceNotFoundException;
import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.JwtAuth.jwt.JwtUtils;
import com.ecommerce.project.JwtAuth.request.LoginRequest;
import com.ecommerce.project.JwtAuth.request.SignupRequest;
import com.ecommerce.project.JwtAuth.request.UpdateUsernameRequest;
import com.ecommerce.project.JwtAuth.response.MessageResponse;
import com.ecommerce.project.JwtAuth.response.UserInfoResponse;
import com.ecommerce.project.JwtAuth.services.UserDetailsImpl;
import com.ecommerce.project.service.Interface.IAddrService;
import com.ecommerce.project.service.Interface.IAuthenticationService;
import com.ecommerce.project.service.Interface.IPaymentCardService;

/**
 * Service implementation for authentication operations
 * Business logic: Handles user registration with optional address/payment info,
 * JWT-based authentication, session management, and profile updates
 */
@Service
@Transactional
public class IAuthenticationServiceImpl implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder encoder;
    private final ModelMapper objectMapper;
    private final IAddrService addressService;
    private final IPaymentCardService paymentCardService;

    public IAuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                                      UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder encoder, ModelMapper objectMapper,
                                      IAddrService addressService, IPaymentCardService paymentCardService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
        this.addressService = addressService;
        this.paymentCardService = paymentCardService;
    }

    // Authenticate user credentials and generate JWT token
    @Override
    public AuthenticationResult login(LoginRequest loginRequest) {
        Authentication authenticationResult = authenticateUserCredentials(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);

        UserDetailsImpl authenticatedUser = (UserDetailsImpl) authenticationResult.getPrincipal();
        ResponseCookie sessionCookie = jwtUtils.generateJwtCookie(authenticatedUser);
        String tokenString = jwtUtils.generateTokenFromUsername(authenticatedUser.getUsername());
        List<String> userAuthorities = extractUserAuthorities(authenticatedUser);

        UserInfoResponse userInfo = buildUserInfoResponse(authenticatedUser, userAuthorities, tokenString);
        return new AuthenticationResult(userInfo, sessionCookie);
    }

    // Register new user with optional address and payment card
    @Override
    public ResponseEntity<MessageResponse> register(SignupRequest signUpRequest) {
        if (userDAO.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("This username has already been registered"));
        }

        if (userDAO.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("This email address is already registered"));
        }

        User newAccount = createNewUserAccount(signUpRequest);
        User persistedUser = userDAO.save(newAccount);

        processOptionalAddressData(signUpRequest, persistedUser);
        processOptionalPaymentCardData(signUpRequest, persistedUser);

        return ResponseEntity.ok(new MessageResponse("Account created successfully!"));
    }

    // Get current user's profile information
    @Override
    public UserInfoResponse getCurrentUserDetails(Authentication authentication) {
        UserDetailsImpl currentUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> userAuthorities = extractUserAuthorities(currentUserDetails);
        return buildUserInfoResponseWithoutToken(currentUserDetails, userAuthorities);
    }

    // Clear JWT cookie to logout user
    @Override
    public ResponseCookie logoutUser() {
        return jwtUtils.getCleanJwtCookie();
    }

    // Get all users with ROLE_USER (customers) with pagination
    @Override
    public UserResponse getAllCustomers(Pageable pageable) {
        Page<User> customerPage = userDAO.findByRoleName(AppRole.ROLE_USER, pageable);
        List<UserDTO> customerDataList = transformCustomersToDTO(customerPage);
        return buildUserResponseData(customerPage, customerDataList);
    }

    // Update username for current user (validates uniqueness)
    @Override
    public ResponseEntity<MessageResponse> updateUsername(UpdateUsernameRequest request, Authentication authentication) {
        String currentlyLoggedInUsername = authentication.getName();
        User existingUser = fetchUserByNameOrThrowException(currentlyLoggedInUsername);

        if (!existingUser.getUserName().equals(request.getUsername()) &&
                userDAO.existsByUserName(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("This username has already been registered"));
        }

        existingUser.setUserName(request.getUsername());
        userDAO.save(existingUser);

        return ResponseEntity.ok(new MessageResponse("Your username has been changed successfully!"));
    }

    private Authentication authenticateUserCredentials(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
    }

    private List<String> extractUserAuthorities(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
    }

    private UserInfoResponse buildUserInfoResponse(UserDetailsImpl user, List<String> authorities, String token) {
        return new UserInfoResponse(user.getId(), user.getUsername(), authorities, user.getEmail(), token);
    }

    private UserInfoResponse buildUserInfoResponseWithoutToken(UserDetailsImpl user, List<String> authorities) {
        return new UserInfoResponse(user.getId(), user.getUsername(), authorities);
    }

    private User createNewUserAccount(SignupRequest signUpRequest) {
        User newAccount = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> assignedRoles = determineUserRoles(signUpRequest.getRole());
        newAccount.setRoles(assignedRoles);

        return newAccount;
    }

    private Set<Role> determineUserRoles(Set<String> requestedRoles) {
        Set<Role> assignedRoles = new HashSet<>();

        if (requestedRoles == null) {
            Role defaultRole = roleDAO.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("The requested role could not be found"));
            assignedRoles.add(defaultRole);
        } else {
            requestedRoles.forEach(roleName -> {
                Role roleToAdd = resolvRoleByName(roleName);
                assignedRoles.add(roleToAdd);
            });
        }

        return assignedRoles;
    }

    private Role resolvRoleByName(String roleName) {
        if ("admin".equals(roleName)) {
            return roleDAO.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("The requested role could not be found"));
        }
        return roleDAO.findByRoleName(AppRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("The requested role could not be found"));
    }

    private void processOptionalAddressData(SignupRequest signUpRequest, User persistedUser) {
        if (signUpRequest.getStreet() != null && !signUpRequest.getStreet().isEmpty()) {
            AddressDto userAddressDto = buildAddressDTO(signUpRequest);
            addressService.newAddr(userAddressDto, persistedUser);
        }
    }

    private AddressDto buildAddressDTO(SignupRequest signUpRequest) {
        AddressDto addressDTO = new AddressDto();
        addressDTO.setStreet(signUpRequest.getStreet());
        addressDTO.setBuildingName(signUpRequest.getBuildingName());
        addressDTO.setCity(signUpRequest.getCity());
        addressDTO.setState(signUpRequest.getState());
        addressDTO.setCountry(signUpRequest.getCountry());
        addressDTO.setPincode(signUpRequest.getPincode());
        return addressDTO;
    }

    private void processOptionalPaymentCardData(SignupRequest signUpRequest, User persistedUser) {
        if (signUpRequest.getCardNumber() != null && !signUpRequest.getCardNumber().isEmpty()) {
            PaymentCardDTO paymentCardDTO = buildPaymentCardDTO(signUpRequest);
            paymentCardService.createPaymentCard(paymentCardDTO, persistedUser);
        }
    }

    private PaymentCardDTO buildPaymentCardDTO(SignupRequest signUpRequest) {
        PaymentCardDTO paymentCardDTO = new PaymentCardDTO();
        paymentCardDTO.setCardNumber(signUpRequest.getCardNumber());
        paymentCardDTO.setCardholderName(signUpRequest.getCardholderName());
        paymentCardDTO.setExpiryMonth(signUpRequest.getExpiryMonth());
        paymentCardDTO.setExpiryYear(signUpRequest.getExpiryYear());
        paymentCardDTO.setCvv(signUpRequest.getCvv());
        paymentCardDTO.setIsDefault(true);
        return paymentCardDTO;
    }

    private List<UserDTO> transformCustomersToDTO(Page<User> customerPage) {
        return customerPage.getContent().stream()
                .map(customerEntity -> objectMapper.map(customerEntity, UserDTO.class))
                .collect(Collectors.toList());
    }

    private UserResponse buildUserResponseData(Page<User> customerPage, List<UserDTO> customerDataList) {
        UserResponse responseData = new UserResponse();
        responseData.setContent(customerDataList);
        responseData.setPageNumber(customerPage.getNumber());
        responseData.setPageSize(customerPage.getSize());
        responseData.setTotalElements(customerPage.getTotalElements());
        responseData.setTotalPages(customerPage.getTotalPages());
        responseData.setLastPage(customerPage.isLast());
        return responseData;
    }

    private User fetchUserByNameOrThrowException(String userName) {
        return userDAO.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", userName));
    }
}
