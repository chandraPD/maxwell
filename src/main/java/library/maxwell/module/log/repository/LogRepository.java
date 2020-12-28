package library.maxwell.module.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.maxwell.module.log.entity.LogEntity;

@Repository
public interface LogRepository  extends JpaRepository<LogEntity, Integer>{

}
