package library.maxwell.module.log.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.log.entity.LogEntity;

@Repository
public interface LogRepository  extends JpaRepository<LogEntity, Integer>{
	Boolean existsByLogId(Integer id);
	
	//QUERY UNTUK MENAMPILKAN DATA LOG YANG DIURUTKAN BERDASARKAN TANGGAL TERBARU
	@Query(value = "SELECT * FROM log ORDER BY date_time DESC ", nativeQuery = true)
	List<LogEntity> findLastActivity();
	
	//QUERY UNTUK MENAMPILKAN DATA LOG USER YANG DIURUTKAN BERDASARKAN TANGGAL TERBARU DAN DIBATASI 20 DATA YANG MUNCUL
	@Query(value = "SELECT * FROM log WHERE user_id = ? ORDER BY date_time DESC LIMIT 20", nativeQuery = true)
	List<LogEntity> findLogUser(Integer id);

}
