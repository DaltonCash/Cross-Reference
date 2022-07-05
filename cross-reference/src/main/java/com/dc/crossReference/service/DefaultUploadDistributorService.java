package com.dc.crossReference.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.crossReference.dao.UploadDistributorDao;
import com.dc.crossReference.entity.Distributor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultUploadDistributorService implements UploadDistributorService {

  @Autowired
  private UploadDistributorDao uploadDistributorDao;

  @Override
  public Distributor createDistributor(String distributor_name, String location, String zip) {
    log.info("in service. name = {}, location = {}, zip ={}", distributor_name, location, zip);
    
    return uploadDistributorDao.createDistributor(distributor_name, location, zip);
  }
}
