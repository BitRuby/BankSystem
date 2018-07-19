package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;
}
