package com.example.smartbite.store.rider_modul.internalModule.controller;


import com.example.smartbite.store.common.element.APIResponse;
import com.example.smartbite.store.rider_modul.DTO.RiderAvailableDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderCreateRequestDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RiderController {
    final private RiderService riderService;

    public RiderController(RiderService riderService){
        this.riderService = riderService;
    }
    @PostMapping("/mark-rider-available")
    public ResponseEntity<APIResponse> makeRiderAvailable(RiderAvailableDTO riderAvailableDTO){
            RiderDTO riderDTO = riderService.makeActiveRiderAndInactive(riderAvailableDTO);

            return ResponseEntity.ok(
                    new APIResponse<>(
                            true,
                            "Mark as activate",
                                    riderDTO
                    )
            );
    }

    @PostMapping("/create-rider-profile")
    public ResponseEntity<APIResponse> createRider(RiderCreateRequestDTO riderCreateDTO){
        RiderDTO rider = riderService.createRider(riderCreateDTO);
        return ResponseEntity .ok(
                new APIResponse<>(
                        true,
                        "Rider profile crated successfully",
                                rider
                )
        );
    }

//    @PostMapping("/mark-rider-unavailable")
//    public ResponseEntity<APIResponse> makeRiderUnavailable(RiderAvailableDTO riderAvailableDTO){
//        RiderDTO rider = riderService.makeRiderInactive(riderAvailableDTO);
//        return  ResponseEntity.ok(
//                new APIResponse<>(
//                        true,
//                        "Rider mark as Inactivate", rider
//                )
//        );
//    }





}
