package com.duoc.LearningPlatform.service;

import com.duoc.LearningPlatform.model.dto.UserRequestDTO;
import com.duoc.LearningPlatform.model.dto.UserResponseDTO;
import com.duoc.LearningPlatform.model.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAll();

    UserResponseDTO getById(Long id);

    UserResponseDTO create(UserRequestDTO dto);

    UserResponseDTO update(Long id, UserUpdateDTO dto);

    void delete(Long id);
}
