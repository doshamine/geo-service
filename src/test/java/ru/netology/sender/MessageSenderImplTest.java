package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {
    @ParameterizedTest
    @CsvSource({
        "172.123.12.19, RUSSIA, Добро пожаловать",
        "96.44.183.149, USA, Welcome",
    })
    @DisplayName("Определение языка приветствия по ip")
    public void givenIp_whenSend_thenReturnCorrectMessage(
        String ip, Country country, String expectedMessage
    ) {
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp(ip))
                .thenReturn(new Location("", country, "", 0));
        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceMock.locale(country))
                .thenReturn(expectedMessage);
        MessageSenderImpl messageSenderImpl = new MessageSenderImpl(
                                                  geoServiceMock, localizationServiceMock
                                              );

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String message = messageSenderImpl.send(headers);

        Assertions.assertEquals(expectedMessage, message);
    }
}
