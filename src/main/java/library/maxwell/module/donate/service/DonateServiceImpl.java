package library.maxwell.module.donate.service;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.donate.dto.DonateDto;
import library.maxwell.module.donate.entity.DonateEntity;
import library.maxwell.module.donate.repository.DonateRepository;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DonateServiceImpl implements DonateService{

    @Autowired
    private DonateRepository donateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;
    @Override
    public List<DonateEntity> getAllDonate() {
        List<DonateEntity> donateEntities = donateRepository.findAll();
        return donateEntities;
    }


    @Override
    public DonateEntity insertDonate(DonateDto dto) {
        DonateEntity donateEntity = convertToDonateEntity(dto);
        donateRepository.save(donateEntity);
        //MENGIRIM AKTIVITAS KE LOG
        LogEntity logEntity = new LogEntity();
        logEntity.setAction("Post");
        logEntity.setDateTime(LocalDateTime.now());
        logEntity.setStatus(true);
        logEntity.setUserEntity(null);
        logEntity.setDescription("Melakukan Penambahan Data Pada Donate List");
        logRepository.save(logEntity);

        return donateEntity;
    }

    @Override
    public DonateEntity updateDonate(UserPrincipal userPrincipal, DonateDto dto, Integer donateId){
        DonateEntity donateEntity = donateRepository.findById(donateId).get();
        UserEntity userEntity = userRepository.findById(userPrincipal.getId()).get();
        donateEntity.setDonationType(dto.getDonationType());
        donateEntity.setEmail(dto.getEmail());
        donateEntity.setName(dto.getName());
        donateEntity.setTotalBook(dto.getTotalBook());
        donateEntity.setUserEntity(userEntity);
        donateRepository.save(donateEntity);
        //MENGIRIM AKTIVITAS KE LOG
        LogEntity logEntity = new LogEntity();
        logEntity.setAction("Put");
        logEntity.setDateTime(LocalDateTime.now());
        logEntity.setStatus(true);
        logEntity.setUserEntity(userEntity);
        logEntity.setDescription("Melakukan Update Data Pada Donate List");
        logRepository.save(logEntity);
        return donateEntity;
    }

    @Override
    public DonateEntity deleteDonate(Integer donateId) {
        DonateEntity donateEntity = donateRepository.findById(donateId).get();
        donateRepository.delete(donateEntity);
        return donateEntity;
    }

    @Override
    public DonateEntity reject(UserPrincipal userPrincipal, Integer donateId) {
        DonateEntity donateEntity = donateRepository.findById(donateId).get();
        UserEntity userEntity = userRepository.findByEmail(userPrincipal.getEmail()).get();
        donateEntity.setStatusDonate("Rejected");
        donateEntity.setUserEntity(userEntity);
        donateRepository.save(donateEntity);
        //MENGIRIM AKTIVITAS KE LOG
        LogEntity logEntity = new LogEntity();
        logEntity.setAction("Reject");
        logEntity.setDateTime(LocalDateTime.now());
        logEntity.setStatus(true);
        logEntity.setUserEntity(userEntity);
        logEntity.setDescription("Melakukan Reject Donate");
        logRepository.save(logEntity);
        return donateEntity;
    }

    @Override
    public DonateEntity accept(UserPrincipal userPrincipal, Integer donateId) {
        DonateEntity donateEntity = donateRepository.findById(donateId).get();
        UserEntity userEntity = userRepository.findByEmail(userPrincipal.getEmail()).get();
        donateEntity.setStatusDonate("Accepted");
        donateEntity.setUserEntity(userEntity);
        donateRepository.save(donateEntity);
        //MENGIRIM AKTIVITAS KE LOG
        LogEntity logEntity = new LogEntity();
        logEntity.setAction("Accept");
        logEntity.setDateTime(LocalDateTime.now());
        logEntity.setStatus(true);
        logEntity.setUserEntity(userEntity);
        logEntity.setDescription("Melakukan Accept Donate");
        logRepository.save(logEntity);
        return donateEntity;
    }

    //    CONVERT METHOD
public DonateEntity convertToDonateEntity(DonateDto dto){
        DonateEntity donateEntity = new DonateEntity();
        donateEntity.setDonationType(dto.getDonationType());
        donateEntity.setEmail(dto.getEmail());
        donateEntity.setName(dto.getName());
        donateEntity.setTotalBook(dto.getTotalBook());
        donateEntity.setStatusDonate(dto.getStatusDonate());
        donateEntity.setPhoneNumber((dto.getPhoneNumber()));
    return donateEntity;
}
}
