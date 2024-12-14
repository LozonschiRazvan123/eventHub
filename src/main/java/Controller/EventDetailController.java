package Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Model.EventDetail;
import Repository.EventDetailRepository;

@RestController
@RequestMapping("/event-details")
public class EventDetailController {

    @Autowired
    private EventDetailRepository eventDetailRepository;

    @GetMapping
    public List<EventDetail> getAllEventDetails() {
        return eventDetailRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetail> getEventDetailById(@PathVariable Long id) {
        Optional<EventDetail> detail = eventDetailRepository.findById(id);
        return detail.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<EventDetail> createEventDetail(@RequestBody EventDetail eventDetail) {
        return new ResponseEntity<>(eventDetailRepository.save(eventDetail), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDetail> updateEventDetail(@PathVariable Long id, @RequestBody EventDetail detailDetails) {
        return eventDetailRepository.findById(id).map(detail -> {
            detail.setSeatNumber(detailDetails.getSeatNumber());
            detail.setSection(detailDetails.getSection());
            detail.setPrice(detailDetails.getPrice());
            detail.setAvailability(detailDetails.getAvailability());
            return ResponseEntity.ok(eventDetailRepository.save(detail));
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventDetail(@PathVariable Long id) {
        if (eventDetailRepository.existsById(id)) {
            eventDetailRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}