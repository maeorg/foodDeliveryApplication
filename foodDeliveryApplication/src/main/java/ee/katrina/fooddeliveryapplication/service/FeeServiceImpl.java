package ee.katrina.fooddeliveryapplication.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FeeServiceImpl implements FeeService {
    @Override
    public double getFee(String city, String vehicleType) throws Exception {
        double RBF = calculateRBF(city, vehicleType);
        double ATEF = calculateATEF(city);
        double WSEF = calculateWSEF(city);
        double WPEF = calculateWPEF(city);
        double totalFee = RBF + ATEF + WSEF + WPEF;
        return totalFee;
    }

    //    Business rules to calculate regional base fee (RBF):
    //        • In case City = Tallinn and:
    //        • Vehicle type = Car, then RBF = 4 €
    //        • Vehicle type = Scooter, then RBF = 3,5 €
    //        • Vehicle type = Bike, then RBF = 3 €
    //        • In case City = Tartu and:
    //        • Vehicle type = Car, then RBF = 3,5 €
    //        • Vehicle type = Scooter, then RBF = 3 €
    //        • Vehicle type = Bike, then RBF = 2,5 €
    //        • In case City = Pärnu and:
    //        • Vehicle type = Car, then RBF = 3 €
    //        • Vehicle type = Scooter, then RBF = 2,5 €
    //        • Vehicle type = Bike, then RBF = 2 €
    private double calculateRBF(String city, String vehicleType) {
        double regionalBaseFee = 0;
        if (Objects.equals(city, "Tallinn")) {
            if (Objects.equals(vehicleType, "Car")) {
                regionalBaseFee = 4;
            } else if (Objects.equals(vehicleType, "Scooter")) {
                regionalBaseFee = 3.5;
            } else if (Objects.equals(vehicleType, "Bike")) {
                regionalBaseFee = 3;
            }
        } else if (Objects.equals(city, "Tartu")) {
            if (Objects.equals(vehicleType, "Car")) {
                regionalBaseFee = 3.5;
            } else if (Objects.equals(vehicleType, "Scooter")) {
                regionalBaseFee = 3;
            } else if (Objects.equals(vehicleType, "Bike")) {
                regionalBaseFee = 2.5;
            }
        } else if (Objects.equals(city, "Pärnu")) {
            if (Objects.equals(vehicleType, "Car")) {
                regionalBaseFee = 3;
            } else if (Objects.equals(vehicleType, "Scooter")) {
                regionalBaseFee = 2.5;
            } else if (Objects.equals(vehicleType, "Bike")) {
                regionalBaseFee = 2;
            }
        }

        return regionalBaseFee;
    }

    //  • Extra fee based on air temperature (ATEF) in a specific city is paid in case Vehicle type = Scooter or Bike and:
    //      • Air temperature is less than -10̊ C, then ATEF = 1 €
    //      • Air temperature is between -10̊ C and 0̊ C, then ATEF = 0,5 €
    private double calculateATEF(String city) {
        double airTemperature = getAirTemperature(city);
        if (airTemperature < -10) {
            return 1;
        } else if (airTemperature >= -10 && airTemperature <= 0) {
            return 0.5;
        }
        return 0;
    }

    //    Extra fee based on wind speed (WSEF) in a specific city is paid in case Vehicle type = Bike and:
    //      • Wind speed is between 10 m/s and 20 m/s, then WSEF = 0,5 €
    //      • In case of wind speed is greater than 20 m/s, then the error message “Usage of selected vehicle type is forbidden” has to be given
    private double calculateWSEF(String city) throws Exception {
        double windSpeed = getWindSpeed(city);
        if (windSpeed >= 10 && windSpeed <= 20) {
            return 0.5;
        } else if (windSpeed > 20) {
            throw new Exception("Usage of selected vehicle type is forbidden");
        }
        return 0;
    }

    //  Extra fee based on weather phenomenon (WPEF) in a specific city is paid in case Vehicle type = Scooter or Bike and:
    //      • Weather phenomenon is related to snow or sleet, then WPEF = 1 €
    //      • Weather phenomenon is related to rain, then WPEF = 0,5 €
    //      • In case the weather phenomenon is glaze, hail, or thunder, then the error message “Usage of selected vehicle type is forbidden” has to be given
    private double calculateWPEF(String city) throws Exception {
        String weatherPhenomen = getWeatherPhenomen(city);

        List<String> snowOrSleetPhenomena = Arrays.asList("Light snow shower", "Moderate snow shower",
                "Heavy snow shower", "Light snowfall", "Moderate snowfall", "Heavy snowfall", "Blowing snow",
                "Drifting snow", "Light sleet", "Moderate sleet");
        List<String> rainPhenomena = Arrays.asList("Light rain", "Moderate rain", "Heavy rain");
        List<String> badPhenomena = Arrays.asList("Glaze", "Hail", "Thunder", "Thunderstorm");

        if (snowOrSleetPhenomena.contains(weatherPhenomen)) {
            return 1;
        } else if (rainPhenomena.contains(weatherPhenomen)) {
            return 0.5;
        } else if (badPhenomena.contains(weatherPhenomen)) {
            throw new Exception("Usage of selected vehicle type is forbidden");
        }
        return 0;
    }
}


