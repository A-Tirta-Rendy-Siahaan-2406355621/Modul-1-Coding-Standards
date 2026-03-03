package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private static final String REDIRECT_CAR_LIST = "redirect:/car/list";

    @Autowired
    private CarService carService;

    @GetMapping("/create")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "createCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car) {
        carService.create(car);
        return REDIRECT_CAR_LIST;
    }

    @GetMapping("/list")
    public String carListPage(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "carList";
    }

    @GetMapping("/edit/{id}")
    public String editCarPage(@PathVariable String id, Model model) {
        Car car = carService.findById(id);
        if (car == null) return REDIRECT_CAR_LIST;
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/update")
    public String updateCar(@ModelAttribute Car car) {
        carService.update(car);
        return REDIRECT_CAR_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable String id) {
        carService.delete(id);
        return REDIRECT_CAR_LIST;
    }
}