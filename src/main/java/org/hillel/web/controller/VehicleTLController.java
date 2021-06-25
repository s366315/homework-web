package org.hillel.web.controller;

import org.hillel.web.controller.converter.VehicleMapper;
import org.hillel.web.controller.dto.VehicleDto;
import org.hillel.web.persistence.entity.VehicleEntity;
import org.hillel.web.service.SearchParams;
import org.hillel.web.service.TicketClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VehicleTLController {

    private final TicketClient ticketClient;
    private final VehicleMapper vehicleMapper;

    public VehicleTLController(TicketClient ticketClient, VehicleMapper vehicleMapper) {
        this.ticketClient = ticketClient;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping("/vehicles")
    public ModelAndView homeVehiclePage(Model model){
        SearchParams searchParams = new SearchParams();
        searchParams.setPageSize(10);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("vehicles", Collections.emptyList());
        return search(model, searchParams);
    }

    @PostMapping("/vehicles/search")
    public ModelAndView search(Model model, @ModelAttribute("searchParams") SearchParams searchParams){
        Collection<VehicleEntity> vehicles = ticketClient.findAllVehicles(
                searchParams.getPageNumber(),
                searchParams.getPageSize(),
                searchParams.getOrderFieldName(),
                searchParams.isOrderAsc(),
                searchParams.getFilterKey(),
                searchParams.getFilterValue());
        List<VehicleDto> vehicleDtos = vehicles.stream().map(vehicleMapper::vehicleToDto).collect(Collectors.toList());
        model.addAttribute("vehicles", vehicleDtos);
        return new ModelAndView("vehicles_view", model.asMap());
    }

    @GetMapping("/vehicle/view/{vehicleId}")
    public ModelAndView view(Model model, @PathVariable("vehicleId") long vehicleId){
        VehicleEntity vehicle = ticketClient.findVehicleById(vehicleId, true);
        VehicleDto vehicleDto = vehicleMapper.fullVehicleToDto(vehicle);
        model.addAttribute("vehicle", vehicleDto);
        return new ModelAndView("vehicle_view", model.asMap());
    }

    @GetMapping("/vehicle/delete/{vehicleId}")
    public RedirectView delete(@PathVariable("vehicleId") long vehicleId){
        ticketClient.removeVehicle(ticketClient.findVehicleById(vehicleId, false));
        return new RedirectView("/tl/vehicles");
    }

    @PostMapping("/vehicle/save")
    public RedirectView save(@ModelAttribute("vehSave") VehicleDto vehicleDto){
        ticketClient.createOrUpdateVechicle(vehicleMapper.dtoToVehicle(vehicleDto));
        return new RedirectView("/tl/vehicles");
    }
}
