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

    @Autowired
    private CarService carService;

    @GetMapping("/create")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "CreateCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car) {
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping("/list")
    public String carListPage(Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "CarList";
    }

    @GetMapping("/edit/{id}")
    public String editCarPage(@PathVariable String id, Model model) {
        Car car = carService.findById(id);
        if (car == null) {
            return "redirect:/car/list";
        }
        model.addAttribute("car", car);
        return "EditCar";
    }

    @PostMapping("/update")
    public String updateCar(@ModelAttribute Car car) {
        carService.update(car);
        return "redirect:/car/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable String id) {
        carService.delete(id);
        return "redirect:/car/list";
    }
}