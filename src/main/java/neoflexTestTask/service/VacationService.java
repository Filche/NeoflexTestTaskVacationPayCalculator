package neoflexTestTask.service;

import neoflexTestTask.client.DayOffFeignClient;
import neoflexTestTask.entity.VacationEntity;
import neoflexTestTask.exception.InvalidVacationDateException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class VacationService {

    private static final Double AVG_NUMBER_DAYS_IN_MONTH = 30.44;
    private final DayOffFeignClient dayOffFeignClient;

    public VacationService(DayOffFeignClient dayOffFeignClient) {
        this.dayOffFeignClient = dayOffFeignClient;
    }

    public String getVacation(VacationEntity entity) {
        if (entity.getSalary() == null || entity.getSalary() <= 0) {
            throw new InvalidVacationDateException("The salary is missing or it is not correct");
        }
        long countDays;
        if (entity.getEndVacation() != null && entity.getEndVacation() != null) {
            countDays = Stream.iterate(entity.getStartVacation(), date -> date.plusDays(1))
                    .limit(entity.getEndVacation().getDayOfMonth() - entity.getStartVacation().getDayOfMonth())
                    .filter((day) -> Objects.equals(dayOffFeignClient.getDayOff(day), "0"))
                    .count();
        } else {
            if (entity.getVacationDays() == null || entity.getVacationDays() <= 0) {
                throw new InvalidVacationDateException("The number of vacation days is missing or it is not correct");
            } else {
                countDays = entity.getVacationDays();
            }
        }
        return String.format("%.2f", entity.getSalary() / AVG_NUMBER_DAYS_IN_MONTH * countDays);
    }
}