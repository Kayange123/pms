package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.UserRepository;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.exception.ApiException;
import dev.kayange.projectmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity findUserByEmail(String email) {
       return userRepository.findByEmail(email).orElseThrow(()->new ApiException("Could not find user with email "+ email));
    }

    @Override
    public UserEntity findUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()->new ApiException("Could not find user with ID "+ userId));
    }

    @Override
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ApiException("Could not find user with ID "+ userId));
    }

    @Override
    public void updateUserProjectSize(UserEntity user, int number) {
        user.setProjectSize(user.getProjectSize() + number);
        userRepository.save(user);
    }
}
