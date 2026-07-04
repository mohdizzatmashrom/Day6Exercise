package com.example.supportdesk.service;

import com.example.supportdesk.dto.CreateTicketRequest;
import com.example.supportdesk.dto.TicketResponse;
import com.example.supportdesk.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final List<TicketResponse> tickets;

    public TicketService() {
        tickets = new ArrayList<>();

        tickets.add(new TicketResponse(
                "T001",
                "Cannot access email",
                "User cannot login to company email account.",
                "Email",
                "HIGH",
                "OPEN",
                "amir@example.com",
                "2026-07-03"
        ));

        tickets.add(new TicketResponse(
                "T002",
                "Laptop is slow",
                "User reports laptop performance is very slow when opening applications.",
                "Hardware",
                "MEDIUM",
                "OPEN",
                "siti@example.com",
                "2026-07-03"
        ));

        tickets.add(new TicketResponse(
                "T003",
                "VPN connection not working",
                "Unable to connect to company VPN from home network.",
                "Network",
                "HIGH",
                "IN_PROGRESS",
                "raj@example.com",
                "2026-07-02"
        ));

        tickets.add(new TicketResponse(
                "T004",
                "Printer not responding",
                "Office printer does not respond to any print jobs.",
                "Hardware",
                "LOW",
                "OPEN",
                "lee@example.com",
                "2026-07-01"
        ));
    }

    public List<TicketResponse> getAllTickets() {
        return tickets;
    }

    public TicketResponse getTicketById(String id) {
        return tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Ticket " + id + " was not found"));
    }

    public TicketResponse createTicket(CreateTicketRequest request) {
        String newId = generateNextId();
        TicketResponse ticket = new TicketResponse(
                newId,
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPriority(),
                "OPEN",
                request.getCreatedBy(),
                LocalDate.now().toString()
        );
        tickets.add(ticket);
        return ticket;
    }

    private String generateNextId() {
        int maxNum = 0;
        for (TicketResponse ticket : tickets) {
            String id = ticket.getId();
            if (id.startsWith("T")) {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxNum) {
                    maxNum = num;
                }
            }
        }
        return String.format("T%03d", maxNum + 1);
    }
}
