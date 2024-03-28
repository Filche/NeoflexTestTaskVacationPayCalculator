package neoflexTestTask.service;

import neoflexTestTask.client.DayOffFeignClient;
import neoflexTestTask.entity.VacationEntity;
import neoflexTestTask.exception.InvalidVacationDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class VacationServiceTest {

    @Mock
    private DayOffFeignClient dayOffFeignClient;

    private VacationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new VacationService(dayOffFeignClient);
    }

    @Test
    void canGetVacationWhenExactDaysAreUnknown() {
        // Given
        VacationEntity entity = new VacationEntity(10, 200000.0);
        // When
        String actual = underTest.getVacation(entity);
        // Then
        assertThat(actual).isEqualTo("65703,02");
    }

    @Test
    void canGetVacationWhenExactDaysAreKnown() {
        // Given
        VacationEntity entity = new VacationEntity(150000.0,
                LocalDate.of(2023, 9, 8),
                LocalDate.of(2023, 9, 13)
        );
        LocalDate day = entity.getStartVacation();
        for (int i = 1; i < 6; i++) {
            given(dayOffFeignClient.getDayOff(day)).willReturn("0");
            day = entity.getStartVacation().plusDays(i);
        }
        // When
        String actual = underTest.getVacation(entity);
        // Then
        assertThat(actual).isEqualTo("24638,63");
    }

    @Test
    void cannotGetVacationWhenVacationEmpty() {
        // Given
        VacationEntity entity = new VacationEntity();
        // When
        Throwable thrown = assertThrows(InvalidVacationDateException.class, () -> {
            underTest.getVacation(entity);
        });
        // Then
        assertNotNull(thrown.getMessage());
    }
}