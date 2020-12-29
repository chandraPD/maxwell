package library.maxwell.module.donate.controller;

import library.maxwell.module.donate.dto.DonateDto;
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

    @GetMapping
    public List<DonateEntity> getAllDonate(){
        List<DonateEntity> donateEntities = donateService.getAllDonate();
        return donateEntities;
    }

    @PostMapping
    public ResponseEntity<?> insertDonate(@RequestBody DonateDto dto){
        DonateEntity donateEntity = donateService.insertDonate(dto);
        return ResponseEntity.ok(donateEntity);
    }

    @PutMapping("/{donateId}")
    public ResponseEntity<?> updateDonate(@PathVariable Integer donateId, @RequestBody DonateDto dto){
        DonateEntity donateEntity = donateService.updateDonate(dto, donateId) ;
        return ResponseEntity.ok(donateEntity);

    }

    @DeleteMapping("/{donateId}")
    public  ResponseEntity<?> deleteDonate(@PathVariable Integer donateId){
        DonateEntity donateEntity  = donateService.deleteDonate(donateId);
        return  ResponseEntity.ok(donateEntity);
    }
}

