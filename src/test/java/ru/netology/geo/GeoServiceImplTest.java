package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceImplTest {
    @ParameterizedTest
    @CsvSource(value = {
            "127.0.0.1, null, null, null, 0",
            "172.0.32.11, Moscow, RUSSIA, Lenina, 15",
            "96.44.183.149, New York, USA, 10th Avenue, 32",
            "172.0.0.0, Moscow, RUSSIA, null, 0",
            "96.0.0.0, New York, USA, null, 0"
    }, nullValues = {"null"}
    )
    @DisplayName("Определение локации по известному ip")
    public void givenKnownIp_whenByIp_thenReturnCorrectLocation(
            String ip, String city, Country country,
            String street, int building
    ) {
        Location expectedLocation = new Location(city, country, street, building);
        GeoService geoServiceImpl = new GeoServiceImpl();

        Location location = geoServiceImpl.byIp(ip);

        Assertions.assertEquals(expectedLocation.getCity(), location.getCity());
        Assertions.assertEquals(expectedLocation.getCountry(), location.getCountry());
        Assertions.assertEquals(expectedLocation.getStreet(), location.getStreet());
        Assertions.assertEquals(expectedLocation.getBuilding(), location.getBuilding());
    }

    @Test
    @DisplayName("Определение локации по неизвестному id возвращает null")
    public void givenUnknownIp_whenByIp_thenReturnNull() {
        String ip = "0.0.0.0";
        GeoService geoServiceImpl = new GeoServiceImpl();

        Location location = geoServiceImpl.byIp(ip);

        Assertions.assertNull(location);
    }
}
