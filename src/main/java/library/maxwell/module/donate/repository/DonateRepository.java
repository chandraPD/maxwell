package library.maxwell.module.donate.repository;

import library.maxwell.module.donate.entity.DonateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonateRepository extends JpaRepository<DonateEntity, Integer> {
}
