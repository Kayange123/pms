package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.entity.UserEntity;

public interface UserService {
  UserEntity findUserByEmail(String email);
  UserEntity findUserByUserId(String userId);
  UserEntity findUserById(Long userId);
  void updateUserProjectSize(UserEntity user, int number);
}
