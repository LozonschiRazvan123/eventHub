package Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Model.*;
import Repository.*;
import Service.ReportService;
import java.io.ByteArrayInputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ReportService reportService;

    @GetMapping
    public String getAllEvents(Model model) {
        try {
            List<Event> events = eventRepository.findAll();
            model.addAttribute("events", events);
            return "events";  
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/new")
    public String showCreateEventForm(Model model) {
        List<User> admins = userRepository.findUserByRole("ADMIN");
        model.addAttribute("event", new Event()); 
        model.addAttribute("admins", admins);    
        return "createEvent"; 
    }



    @PostMapping
    public String createEvent(@ModelAttribute Event event, Model model) {
        try {
            User organizer = userRepository.findById(event.getOrganizer().getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid organizer ID"));
            event.setOrganizer(organizer); 

            event.setCreatedAt(new Date());
            event.setUpdatedAt(new Date());
            eventRepository.save(event); 
            return "redirect:/events"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while creating the event.");
            return "createEvent"; 
        }
    }




	/*
	 * @PutMapping("/{id}") public ResponseEntity<Event> updateEvent(@PathVariable
	 * Long id, @RequestBody Event eventDetails) { return
	 * eventRepository.findById(id).map(event -> { //event.setOrganizer(organizer);
	 * event.setTitle(event.getTitle());
	 * event.setDescription(event.getDescription());
	 * event.setLocation(event.getLocation());
	 * event.setMaxTickets(event.getMaxTickets()); event.setCreatedAt(new Date());
	 * event.setUpdatedAt(new Date()); return
	 * ResponseEntity.ok(eventRepository.save(event)); }).orElse(new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND)); }
	 */
    
    @GetMapping("/{id}/edit")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));
        model.addAttribute("event", event);  // Adaugă evenimentul existent pentru a-l edita
        model.addAttribute("admins", userRepository.findUserByRole("ADMIN"));  // Adaugă administratori pentru selecție
        return "editEvent";  // Afișează formularul de editare eveniment
    }

    @PostMapping("/{id}/update")
    public String updateEvent(@PathVariable Long id, @ModelAttribute Event event, Model model) {
        try {
            Event existingEvent = eventRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));

            existingEvent.setTitle(event.getTitle());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setLocation(event.getLocation());
            existingEvent.setMaxTickets(event.getMaxTickets());
            existingEvent.setUpdatedAt(new Date()); 

            eventRepository.save(existingEvent);  

            return "redirect:/events"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while updating the event.");
            return "editEvent"; 
        }
    }
    


    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));
        eventRepository.delete(event);
        return "redirect:/events"; 
    }
    
    
    @GetMapping("/{eventId}/report")
    public ResponseEntity<InputStreamResource> downloadReport(
            @PathVariable Long eventId,
            @RequestParam String format) {

        try {
            byte[] reportContent = reportService.generateReport(eventId, format);


            HttpHeaders headers = new HttpHeaders();
            String fileName = "report";
            
            if ("pdf".equalsIgnoreCase(format)) {
                fileName += ".pdf";
                headers.setContentType(MediaType.APPLICATION_PDF);
            } else if ("excel".equalsIgnoreCase(format)) {
                fileName += ".xlsx";  
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            } else {
                return ResponseEntity.badRequest().build();
            }


            headers.setContentDispositionFormData("attachment", fileName);


            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(reportContent));
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
}
}
