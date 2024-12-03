package org.abdellah.ebankify1.service;


import org.abdellah.ebankify1.model.User;
import org.abdellah.ebankify1.repository.UserRepository;
import org.abdellah.ebankify1.dto.UserDTO;
import org.abdellah.ebankify1.exception.UserAlreadyExistsException;
import org.abdellah.ebankify1.exception.UserNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        existingUser.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}