package ee.katrina.fooddeliveryapplication.service;

public interface FeeService {
    double getFee(String city, String vehicleType) throws Exception;
}
