package Controller;

import Model.Event;
import Model.Payment;
import Model.User;
import Repository.EventRepository;
import Repository.FeedbackRepository;
import Repository.PaymentRepository;
import Repository.ReservationRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping
    public String listPayments(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        if (page < 1) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("paymentDate").descending());
        Page<Payment> paymentsPage;

        if (search != null && !search.isEmpty()) {
            paymentsPage = paymentRepository.findByReservation_Event_TitleContainingIgnoreCase(search, pageable);
        } else {
            paymentsPage = paymentRepository.findAll(pageable);
        }

        int totalPages = paymentsPage.getTotalPages();
        int currentPage = paymentsPage.getNumber();

        int visiblePageCount = 5;
        List<Integer> visiblePages = calculateVisiblePages(currentPage, totalPages, visiblePageCount);

        model.addAttribute("payments", paymentsPage.getContent());
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("visiblePages", visiblePages.stream().map(p -> p + 1).collect(Collectors.toList()));
        model.addAttribute("search", search); // Trimite termenul de căutare înapoi la template

        return "payment-list";
    }

    private List<Integer> calculateVisiblePages(int currentPage, int totalPages, int visiblePageCount) {
        int startPage = Math.max(0, currentPage - visiblePageCount / 2);
        int endPage = Math.min(totalPages - 1, startPage + visiblePageCount - 1);

        if (endPage - startPage + 1 < visiblePageCount) {
            startPage = Math.max(0, endPage - visiblePageCount + 1);
        }

        return IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String addPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("users", userRepository.findAll()); 
        model.addAttribute("events", eventRepository.findAll()); 
        return "payment-add";
    }

    @PostMapping("/add")
    public String savePayment(
            @ModelAttribute Payment payment,
            @RequestParam Long userId,
            @RequestParam Long eventId) {
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizator invalid: " + userId));

        Event selectedEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Eveniment invalid: " + eventId));

        payment.setUser(selectedUser);
        payment.setEvent(selectedEvent);

        paymentRepository.save(payment);

        return "redirect:/payments";
    }
    @GetMapping("/edit/{id}")
    public String editPaymentForm(@PathVariable Long id, Model model) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if (optionalPayment.isPresent()) {
            model.addAttribute("payment", optionalPayment.get());
            model.addAttribute("users", userRepository.findAll()); 
            model.addAttribute("events", eventRepository.findAll()); 
            return "payment-edit"; 
        } else {
            return "redirect:/payments";
        }
    }
    
    @PostMapping("/edit/{id}")
    public String updatePayment(
            @PathVariable Long id,
            @ModelAttribute Payment payment,
            @RequestParam Long userId,
            @RequestParam Long eventId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if (optionalPayment.isPresent()) {
            Payment existingPayment = optionalPayment.get();

            User selectedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Utilizator invalid: " + userId));

            Event selectedEvent = eventRepository.findById(eventId)
                    .orElseThrow(() -> new IllegalArgumentException("Eveniment invalid: " + eventId));

            existingPayment.setAmount(payment.getAmount());
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setStatus(payment.getStatus());
            existingPayment.setPaymentDate(payment.getPaymentDate());
            existingPayment.setUser(selectedUser);
            existingPayment.setEvent(selectedEvent);

            paymentRepository.save(existingPayment);
        }

        return "redirect:/payments";
    }
    
    @DeleteMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id); 
        }
        return "redirect:/payments"; 
    }
    
    @GetMapping("/delete/{id}")
    public String deletePaymentRedirect(@PathVariable Long id) {
        if (paymentRepository.existsById(id)) {
        	feedbackRepository.deleteById(id);
            paymentRepository.deleteById(id);
        }
        return "redirect:/payments";
    }


}
