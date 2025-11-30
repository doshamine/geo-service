package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;

public class LocalizationServiceImplTest {
    @ParameterizedTest
    @CsvSource(
        value = {
            "RUSSIA, Добро пожаловать",
            "GERMANY, Welcome",
            "BRAZIL, Welcome",
            "USA, Welcome"
        },
        nullValues = {"null"}
    )
    @DisplayName("Возвращаемый текст зависит от локали")
    public void givenCountry_whenLocale_returnCorrectText(
            Country country, String expected
    ) {
        LocalizationService service = new LocalizationServiceImpl();

        String message = service.locale(country);

        Assertions.assertEquals(expected, message);
    }
}
