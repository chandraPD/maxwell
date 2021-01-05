package library.maxwell.module.user.repository;

import library.maxwell.module.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    
    @Query(value="select password from user where user_id=?",nativeQuery = true)
    String findPasswordByUserId(Integer id);
}
