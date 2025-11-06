package hiber.service;

import hiber.model.Car;
import hiber.model.User;

import java.util.List;

public interface UserService {
    void addCar(Car car);
    void addUser(User user, Car car);
    List<User> getAllUsers();
    User findUserByCar(String model, int series);
}
