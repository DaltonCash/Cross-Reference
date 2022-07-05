package com.dc.crossReference.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dc.crossReference.entity.Business;
import com.dc.crossReference.service.UploadBusinessService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultUploadBusinessController implements UploadBusinessController {

  @Autowired
  public UploadBusinessService uploadBusinessService;

  @Override
  public Business createBusiness(String fbHandle) throws IOException {
    log.info(
        "A request to build a business has been recieved by the controller, going to service with handle: fb={}.",
        fbHandle);

    return uploadBusinessService.createBusiness(fbHandle);
  }
}
