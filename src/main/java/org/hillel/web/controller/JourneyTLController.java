package org.hillel.web.controller;

import org.hillel.web.controller.converter.JourneyMapper;
import org.hillel.web.controller.dto.JourneyDto;
import org.hillel.web.persistence.entity.JourneyEntity;
import org.hillel.web.service.SearchParams;
import org.hillel.web.service.TicketClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JourneyTLController {

    private final TicketClient ticketClient;
    private final JourneyMapper journeyMapper;

    public JourneyTLController(TicketClient ticketClient, JourneyMapper journeyMapper) {
        this.ticketClient = ticketClient;
        this.journeyMapper = journeyMapper;
    }

    @GetMapping("/journeys")
    public ModelAndView homeJourneyPage(Model model){
        SearchParams searchParams = new SearchParams();
        searchParams.setPageSize(10);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("journeys", Collections.emptyList());
        return search(model, searchParams);
    }

    @PostMapping("/journeys/search")
    public ModelAndView search(Model model, @ModelAttribute("searchParams") SearchParams searchParams){
        Collection<JourneyEntity> vehicles = ticketClient.findAllJourneys(
                searchParams.getPageNumber(),
                searchParams.getPageSize(),
                searchParams.getOrderFieldName(),
                searchParams.isOrderAsc(),
                searchParams.getFilterKey(),
                searchParams.getFilterValue());
        List<JourneyDto> journeyDtos = vehicles.stream().map(journeyMapper::journeyToDto).collect(Collectors.toList());
        model.addAttribute("journeys", journeyDtos);
        return new ModelAndView("journeys_view", model.asMap());
    }

    @GetMapping("/journey/view/{journeyId}")
    public ModelAndView view(Model model, @PathVariable("journeyId") long journeyId){
        JourneyEntity journey = ticketClient.findJourneyById(journeyId, true).get();
        JourneyDto journeyDto = journeyMapper.fullJourneyToDto(journey);
        model.addAttribute("journey", journeyDto);
        return new ModelAndView("journey_view", model.asMap());
    }

}
