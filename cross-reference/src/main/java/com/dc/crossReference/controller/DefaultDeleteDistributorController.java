package com.dc.crossReference.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dc.crossReference.entity.Distributor;
import com.dc.crossReference.service.DeleteDistributorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultDeleteDistributorController implements DeleteDistributorController {

  @Autowired
  private DeleteDistributorService deleteDistributorService;
  
  @Override
  public Distributor deleteDistributor(int distributor_id) {
    log.info("Delete request for distributor_id = {} in Controller", distributor_id);
    
    return deleteDistributorService.deleteDistributor(distributor_id);
  }
}
