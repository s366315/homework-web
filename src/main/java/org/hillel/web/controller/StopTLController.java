package org.hillel.web.controller;

import org.hillel.web.controller.converter.StopMapper;
import org.hillel.web.controller.dto.StopDto;
import org.hillel.web.persistence.entity.StopEntity;
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
public class StopTLController {

    private final TicketClient ticketClient;
    private final StopMapper stopMapper;

    public StopTLController(TicketClient ticketClient, StopMapper stopMapper) {
        this.ticketClient = ticketClient;
        this.stopMapper = stopMapper;
    }

    @GetMapping("/stops")
    public ModelAndView homeStopPage(Model model){
        SearchParams searchParams = new SearchParams();
        searchParams.setPageSize(10);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("stops", Collections.emptyList());
        return search(model, searchParams);
    }

    @PostMapping("/stops/search")
    public ModelAndView search(Model model, @ModelAttribute("searchParams") SearchParams searchParams){
        Collection<StopEntity> stop = ticketClient.findAllStops(
                searchParams.getPageNumber(),
                searchParams.getPageSize(),
                searchParams.getOrderFieldName(),
                searchParams.isOrderAsc(),
                searchParams.getFilterKey(),
                searchParams.getFilterValue());
        List<StopDto> stopDtos = stop.stream().map(stopMapper::stopToDto).collect(Collectors.toList());
        model.addAttribute("stops", stopDtos);
        return new ModelAndView("stops_view", model.asMap());
    }

    @GetMapping("/stop/view/{stopId}")
    public ModelAndView view(Model model, @PathVariable("stopId") long stopId){
        StopEntity stop = ticketClient.findStopById(stopId, true);
        StopDto stopDto = stopMapper.fullStopToDto(stop);
        model.addAttribute("stop", stopDto);
        return new ModelAndView("stop_view", model.asMap());
    }
}
