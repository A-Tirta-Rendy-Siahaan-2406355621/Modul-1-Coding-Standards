package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService){
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    // =========================
    // CREATE ORDER PAGE
    // =========================
    @GetMapping("/create")
    public String createPage(){
        return "order-create";
    }

    // =========================
    // CREATE ORDER ACTION
    // =========================
    @PostMapping("/create")
    public String createOrder(
            @RequestParam String author,
            @RequestParam String productName,
            @RequestParam int quantity){

        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName(productName);
        product.setProductQuantity(quantity);

        List<Product> products = new ArrayList<>();
        products.add(product);

        Order order = new Order(
                UUID.randomUUID().toString(),
                products,
                System.currentTimeMillis(),
                author
        );

        orderService.createOrder(order);

        return "redirect:/order/history?author=" + author;
    }

    // =========================
    // ORDER HISTORY PAGE
    // =========================
    @GetMapping("/history")
    public String historyPage(
            @RequestParam(required = false) String author,
            Model model){

        if(author != null){
            List<Order> orders = orderService.findAllByAuthor(author);
            model.addAttribute("orders", orders);
            model.addAttribute("author", author);
        }

        return "order-history";
    }

    // =========================
    // PAYMENT PAGE
    // =========================
    @GetMapping("/pay/{orderId}")
    public String payPage(
            @PathVariable String orderId,
            Model model){

        model.addAttribute("orderId", orderId);
        return "order-pay";
    }

    // =========================
    // PROCESS PAYMENT
    // =========================
    @PostMapping("/pay/{orderId}")
    public String payOrder(
            @PathVariable String orderId,
            @RequestParam String voucherCode,
            Model model){

        Order order = orderService.findById(orderId);

        Map<String,String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", voucherCode);

        Payment payment = paymentService.addPayment(
                order,
                "Voucher Code",
                paymentData
        );

        model.addAttribute("payment", payment);

        return "payment-detail";
    }
}