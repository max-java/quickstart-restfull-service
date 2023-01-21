package com.tutrit.quickstart.restfull.servise.controller;

import com.tutrit.quickstart.restfull.servise.bean.GhbUser;
import com.tutrit.quickstart.restfull.servise.bean.Master;
import com.tutrit.quickstart.restfull.servise.repository.MasterRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@RestController
public class MasterSearchController {
    private final MasterRepository masterRepository;

    public MasterSearchController(final MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    // TODO: 8/22/22 add mvc test
    @GetMapping("/masters/advanced_search/findAllMasterUsers")
    public List<GhbUser> findAllMasterTelegramChatIds() {
        return stream(masterRepository.findAll().spliterator(), false)
                .map(Master::getGhbUser)
                .collect(toList());
    }
}
