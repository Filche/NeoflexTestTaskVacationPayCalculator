package neoflexTestTask.controller;

import neoflexTestTask.entity.VacationEntity;
import neoflexTestTask.exception.InvalidVacationDateException;
import neoflexTestTask.service.VacationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class VacationController {

    private final VacationService service;

    public VacationController(VacationService service) {
        this.service = service;
    }

    @GetMapping("/calculate")
    public ResponseEntity<?> getVacation(@RequestBody VacationEntity entity) {
        try {
            return new ResponseEntity<>(service.getVacation(entity), HttpStatus.OK);
        } catch (InvalidVacationDateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/calculate")
//    public ResponseEntity<?> getVacation(@RequestParam Integer vacationDays,
//                                         @RequestParam Double salary,
//                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startVacation,
//                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endVacation) {
//        if (startVacation.isPresent() && endVacation.isPresent()){
//            try {
//                return new ResponseEntity<>(service.getVacation(new VacationEntity(salary, startVacation.get(), endVacation.get())), HttpStatus.OK);
//            } catch (InvalidVacationDateException e) {
//                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//            }
//        }
//        try {
//            return new ResponseEntity<>(service.getVacation(new VacationEntity(vacationDays, salary)), HttpStatus.OK);
//        } catch (InvalidVacationDateException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
}

