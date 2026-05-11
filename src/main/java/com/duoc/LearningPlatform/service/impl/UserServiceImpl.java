package com.duoc.LearningPlatform.service.impl;

import com.duoc.LearningPlatform.exception.ResourceNotFoundException;
import com.duoc.LearningPlatform.model.User;
import com.duoc.LearningPlatform.model.dto.UserRequestDTO;
import com.duoc.LearningPlatform.model.dto.UserResponseDTO;
import com.duoc.LearningPlatform.model.dto.UserUpdateDTO;
import com.duoc.LearningPlatform.repository.UserRepository;
import com.duoc.LearningPlatform.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        User user = new User(
                dto.getName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getRole()
        );
        return new UserResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        if (dto.getName() == null && dto.getEmail() == null
                && dto.getPassword() == null && dto.getRole() == null) {
            throw new IllegalArgumentException(
                    "Debe proporcionar al menos un campo válido para actualizar: name, email, password, role");
        }
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        if (dto.getName()     != null) existing.setName(dto.getName());
        if (dto.getEmail()    != null) existing.setEmail(dto.getEmail());
        if (dto.getPassword() != null) existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRole()     != null) existing.setRole(dto.getRole());
        return new UserResponseDTO(userRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }
        userRepository.deleteById(id);
    }
}
