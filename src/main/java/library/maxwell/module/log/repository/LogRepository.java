package library.maxwell.module.log.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.log.entity.LogEntity;

@Repository
public interface LogRepository  extends JpaRepository<LogEntity, Integer>{
	@Query(value = "SELECT * FROM log ORDER BY date_time DESC ", nativeQuery = true)
	List<LogEntity> findLastActivity();
	
	@Query(value = "SELECT * FROM log WHERE user_id = ? ORDER BY date_time DESC LIMT 3", nativeQuery = true)
	List<LogEntity> findLogUser(Integer id);
	
//	List<LogEntity> findByUserEntity_UserIdIs(Integer userId);
}
