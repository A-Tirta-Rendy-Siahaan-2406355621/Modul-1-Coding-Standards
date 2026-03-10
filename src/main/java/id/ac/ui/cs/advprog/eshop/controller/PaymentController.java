package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // GET /payment/detail
    @GetMapping("/detail")
    public String paymentDetailForm() {
        return "payment-detail";
    }

    // GET /payment/detail/{paymentId}
    @GetMapping("/detail/{paymentId}")
    public String paymentDetail(@PathVariable String paymentId, Model model) {

        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);

        return "payment-detail";
    }

    // GET /payment/admin/list
    @GetMapping("/admin/list")
    public String adminList(Model model) {

        model.addAttribute("payments", paymentService.getAllPayments());

        return "payment-admin-list";
    }

    // GET /payment/admin/detail/{paymentId}
    @GetMapping("/admin/detail/{paymentId}")
    public String adminDetail(@PathVariable String paymentId, Model model) {

        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);

        return "payment-admin-detail";
    }

    // POST /payment/admin/set-status/{paymentId}
    @PostMapping("/admin/set-status/{paymentId}")
    public String setStatus(
            @PathVariable String paymentId,
            @RequestParam String status) {

        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);

        return "redirect:/payment/admin/list";
    }
}