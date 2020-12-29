package library.maxwell.module.donate.repository;

import library.maxwell.module.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserId(Integer UserId);
}
