package library.maxwell.module.donate.controller;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.donate.dto.DonateDto;
import library.maxwell.module.donate.dto.StatusMessageDto;
import library.maxwell.module.donate.entity.DonateEntity;
import library.maxwell.module.donate.service.DonateService;
import library.maxwell.module.donate.service.DonateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donate")

public class DonateController {
    @Autowired
    private DonateServiceImpl donateService;

    private StatusMessageDto statusMessageDto = new StatusMessageDto();

    @GetMapping
    public ResponseEntity<?> getAllDonate() {
        List<DonateEntity> donateEntities = donateService.getAllDonate();
        statusMessageDto.setStatus(200);
        statusMessageDto.setMessages("Data have succesfully loaded");
        statusMessageDto.setData(donateEntities);
        return ResponseEntity.ok(statusMessageDto);
    }

    @PostMapping
    public ResponseEntity<?> insertDonate(@RequestBody DonateDto dto) {
        DonateEntity donateEntity = donateService.insertDonate(dto);
        statusMessageDto.setStatus(200);
        statusMessageDto.setMessages("Data have succesfully loaded");
        statusMessageDto.setData(donateEntity);
        return ResponseEntity.ok(statusMessageDto);
    }


    @PostMapping("/accept/{donateId}")
    public ResponseEntity<?> accept(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer donateId) {
        DonateEntity donateEntity = donateService.accept(userPrincipal, donateId);
        statusMessageDto.setStatus(200);
        statusMessageDto.setMessages("Data have succesfully accepted");
        statusMessageDto.setData(donateEntity);
        return ResponseEntity.ok(statusMessageDto);
    }

    @PostMapping("/reject/{donateId}")
    public ResponseEntity<?> reject(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer donateId) {
        DonateEntity donateEntity = donateService.reject(userPrincipal, donateId);
        statusMessageDto.setStatus(200);
        statusMessageDto.setMessages("Data have succesfully rejected");
        statusMessageDto.setData(donateEntity);
        return ResponseEntity.ok(statusMessageDto);
    }
}

