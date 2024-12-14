package Controller;

import Model.Event;
import Model.Reservation;
import Model.User;
import Repository.EventRepository;
import Repository.ReservationRepository;
import Repository.UserRepository;

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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public String getAllReservations(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "30") int size,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("event.title").ascending());

        Page<Reservation> reservationsPage;

        if (search != null && !search.isEmpty()) {
            reservationsPage = reservationRepository.findByEventTitleAndActiveStatus(search, pageable);
        } else {
            reservationsPage = reservationRepository.findActiveReservations(pageable);
        }

        int totalPages = reservationsPage.getTotalPages();
        int currentPage = reservationsPage.getNumber();

        int visiblePageCount = 5;
        List<Integer> visiblePages = calculateVisiblePages(currentPage, totalPages, visiblePageCount);

        model.addAttribute("reservations", reservationsPage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("visiblePages", visiblePages);
        model.addAttribute("search", search);

        return "reservations";
    }


    private List<Integer> calculateVisiblePages(int currentPage, int totalPages, int visiblePageCount) {
        int startPage = Math.max(currentPage - visiblePageCount / 2, 0);
        int endPage = Math.min(startPage + visiblePageCount - 1, totalPages - 1);

        if (endPage - startPage + 1 < visiblePageCount) {
            startPage = Math.max(endPage - visiblePageCount + 1, 0);
        }

        return IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    }

    
    @GetMapping("/add")
    public String showAddReservationForm(Model model) {
        Reservation reservation = new Reservation();

        List<Event> events = eventRepository.findAll();
        model.addAttribute("reservationRequest", reservation);
        model.addAttribute("events", events);
        return "addReservation";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	/*
	 * @PostMapping public ResponseEntity<Reservation>
	 * createReservation(@RequestBody Reservation reservation) { try { Reservation
	 * savedReservation = reservationRepository.save(reservation); return new
	 * ResponseEntity<>(savedReservation, HttpStatus.CREATED); } catch (Exception e)
	 * { e.printStackTrace(); return new
	 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	/*
	 * @PutMapping("/{id}") public ResponseEntity<Reservation>
	 * updateReservation(@PathVariable Long id, @RequestBody Reservation
	 * reservationDetails) { return
	 * reservationRepository.findById(id).map(reservation -> {
	 * reservation.setNumberOfTickets(reservationDetails.getNumberOfTickets());
	 * reservation.setPaymentStatus(reservationDetails.getPaymentStatus());
	 * reservation.setQrCode(reservationDetails.getQrCode());
	 * reservation.setReservationDate(reservationDetails.getReservationDate());
	 * return ResponseEntity.ok(reservationRepository.save(reservation));
	 * }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); }
	 */
    @PostMapping
    public String createReservation(@ModelAttribute("reservationRequest") Reservation reservationRequest, Model model) {
        Event event = eventRepository.findById(reservationRequest.getEvent().getEventId())
                                     .orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));

        if (reservationRequest.getNumberOfTickets() > event.getMaxTickets()) {
            model.addAttribute("message", "The number of tickets exceeds the available tickets for this event.");
            model.addAttribute("events", eventRepository.findAll());
            return "addReservation";
        }
        User currentUser = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("User not found"));
        reservationRequest.setUser(currentUser);
        reservationRequest.setEvent(event);
        reservationRequest.setReservationDate(new Date());

        reservationRepository.save(reservationRequest);

        model.addAttribute("message", "Reservation created successfully!");
        return "redirect:/reservations";
    }

    @PostMapping("/{id}/cancel")
    public String cancelReservation(@PathVariable Long id, Model model) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Invalid reservation ID: " + id));
        
        if (reservation.getStatus() == null) {
            model.addAttribute("error", "Reservation is already inactive.");
            return "reservations"; 
        }

        reservation.setStatus("inactiv");
        reservationRepository.save(reservation);

        model.addAttribute("message", "Reservation cancelled successfully.");
        return "redirect:/reservations"; 
    }

}
