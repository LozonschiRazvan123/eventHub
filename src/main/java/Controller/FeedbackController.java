package Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Model.Event;
import Model.Feedback;
import Model.Payment;
import Model.User;
import Repository.*;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public String getFeedbacks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "30") int size,
            @RequestParam(value = "eventId", required = false) Long eventId,
            @RequestParam(value = "userId", required = false) Long userId,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbackPage;

        // Filtrare după eventId sau userId
        if (eventId != null) {
            feedbackPage = feedbackRepository.findByEventEventId(eventId, pageable);
        } else {
            feedbackPage = feedbackRepository.findAll(pageable);
        }

        if (feedbackPage.isEmpty()) {
            model.addAttribute("error", "No feedbacks found.");
            return "error";
        }

        int totalPages = feedbackPage.getTotalPages();
        int currentPage = feedbackPage.getNumber();
        int visiblePageCount = 5;

        List<Integer> visiblePages = calculateVisiblePages(currentPage, totalPages, visiblePageCount);


        List<Event> events = eventRepository.findAll();

        model.addAttribute("events", events); 
        model.addAttribute("feedbacks", feedbackPage.getContent());
        model.addAttribute("feedbackPage", feedbackPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("visiblePages", visiblePages);
        model.addAttribute("selectedEventId", eventId); 

        return "feedback-list";
    }


    private List<Integer> calculateVisiblePages(int currentPage, int totalPages, int visiblePageCount) {
        int startPage = Math.max(currentPage - visiblePageCount / 2, 0);  
        int endPage = Math.min(startPage + visiblePageCount - 1, totalPages - 1); 

        if (endPage - startPage + 1 < visiblePageCount) {
            startPage = Math.max(endPage - visiblePageCount + 1, 0);
        }

        return IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        return feedbackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public String saveFeedback(@ModelAttribute Feedback feedback, Model model) {
        try {
            Event event = eventRepository.findById(feedback.getEvent().getEventId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));

            User user = userRepository.findById(feedback.getUser().getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

            feedback.setEvent(event);
            feedback.setUser(user);

            feedbackRepository.save(feedback); 

            return "redirect:/feedbacks?page=0&size=10";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while saving feedback.");
            return "feedback-form"; 
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedbackDetails) {
        return feedbackRepository.findById(id).map(feedback -> {
            feedback.setRating(feedbackDetails.getRating());
            feedback.setComments(feedbackDetails.getComments());
            return ResponseEntity.ok(feedbackRepository.save(feedback));
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id); 
        }
        return "redirect:/feedbacks?page=0&size=10";
    }
    


    @GetMapping("/new")
    public String addFeedbackForm(Model model) {
        List<Event> events = eventRepository.findAll(); 
        model.addAttribute("events", events);
        
        User user = userRepository.getById(1L); 
        model.addAttribute("user", user);
        model.addAttribute("feedback", new Feedback());

        return "feedback-form"; 
    }

	/*
	 * @PostMapping("/save") public String saveFeedback(@ModelAttribute Feedback
	 * feedback) { feedbackRepository.save(feedback); return "all"; }
	 */
}
