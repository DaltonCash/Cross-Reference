package com.dc.crossReference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dc.crossReference.entity.Distributor;
import com.dc.crossReference.service.UploadDistributorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultUploadDistributorController implements UploadDistributorController {

  @Autowired
  public UploadDistributorService uploadDistributorService;

  @Override
  public Distributor createDistributor(String distributor_name, String location, String zip) {
    log.info("A request has been picked up by the controller to make a new distributor \n"
        + "name = {}, location = {}, zip = {}", distributor_name, location, zip);

    return uploadDistributorService.createDistributor(distributor_name, location, zip);
  }
}
