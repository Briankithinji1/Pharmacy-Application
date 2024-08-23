package com.brytech.messaging_service.service;

import com.brytech.messaging_service.dto.UserDto;
import com.brytech.messaging_service.enums.UserStatus;
import com.brytech.messaging_service.exceptions.UserNotFoundException;
import com.brytech.messaging_service.model.User;
import com.brytech.messaging_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void saveUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        user.setOnlineStatus(UserStatus.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(String userId) {
        User storedUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        storedUser.setOnlineStatus(UserStatus.OFFLINE);
        userRepository.save(storedUser);
    }

    public List<UserDto> findConnectUsers() {
        return userRepository.findAllByStatus(UserStatus.ONLINE)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return convertToDto(user);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
