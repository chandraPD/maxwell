package library.maxwell.module.donate.service;

import library.maxwell.module.donate.dto.DonateDto;
import library.maxwell.module.donate.entity.DonateEntity;
import library.maxwell.module.donate.repository.DonateRepository;
import library.maxwell.module.donate.repository.UserRepository;
import library.maxwell.module.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateServiceImpl implements DonateService{

    @Autowired
    private DonateRepository donateRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<DonateEntity> getAllDonate() {
        List<DonateEntity> donateEntities = donateRepository.findAll();
        return donateEntities;
    }


    @Override
    public DonateEntity insertDonate(DonateDto dto) {
        DonateEntity donateEntity = convertToDonateEntity(dto);
        UserEntity userEntity = userRepository.findById(1).get();
        donateEntity.setUserEntity(userEntity);
        donateRepository.save(donateEntity);

        return donateEntity;
    }

    @Override
    public DonateEntity updateDonate(DonateDto dto, Integer donateId){
        DonateEntity donateEntity = donateRepository.findById(donateId).get();
        UserEntity userEntity = userRepository.findById(1).get();
        donateEntity.setDonationType(dto.getDonationType());
        donateEntity.setCreatedAt(dto.getCreatedAt());
        donateEntity.setEmail(dto.getEmail());
        donateEntity.setName(dto.getName());
        donateEntity.setTotalBook(dto.getTotalBook());
        donateEntity.setUserEntity(userEntity);
        donateRepository.save(donateEntity);
        return donateEntity;
    }

    @Override
    public DonateEntity deleteDonate(Integer donateId) {
        DonateEntity donateEntity = donateRepository.findById(donateId).get();
        donateRepository.delete(donateEntity);
        return donateEntity;
    }
//    CONVERT METHOD
public DonateEntity convertToDonateEntity(DonateDto dto){
        DonateEntity donateEntity = new DonateEntity();
        donateEntity.setDonationType(dto.getDonationType());
        donateEntity.setCreatedAt(dto.getCreatedAt());
        donateEntity.setEmail(dto.getEmail());
        donateEntity.setName(dto.getName());
        donateEntity.setTotalBook(dto.getTotalBook());
    return donateEntity;
}
}
