package library.maxwell.module.donate.service;

import library.maxwell.module.donate.dto.DonateDto;
import library.maxwell.module.donate.entity.DonateEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface DonateService {
    List<DonateEntity> getAllDonate();
    DonateEntity insertDonate(DonateDto dto);
    DonateEntity updateDonate(DonateDto dto, Integer UserId);
    DonateEntity deleteDonate(Integer UserId);
}
