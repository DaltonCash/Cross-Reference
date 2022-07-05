package com.dc.crossReference.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dc.crossReference.entity.Distributor;
import com.dc.crossReference.service.GetDistributorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultGetDistributorController implements GetDistributorController {

  @Autowired
  private GetDistributorService getDistributorService;
  
  @Override
  public List<Distributor> getDistributors() {
    log.info("Getting Distributors in Controller");
    
    return getDistributorService.getDistributors();
  }
}