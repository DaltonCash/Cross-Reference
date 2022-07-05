package com.dc.crossReference.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dc.crossReference.entity.Business;
import com.dc.crossReference.service.GetBusinessService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultGetBusinessController implements GetBusinessController {

  @Autowired
  private GetBusinessService getBusinessService;
  
  @Override
  public List<Business> getBusinesses() {
    log.info("Getting business searches in Controller");
    
    return getBusinessService.getBusinesses();
  }
}
