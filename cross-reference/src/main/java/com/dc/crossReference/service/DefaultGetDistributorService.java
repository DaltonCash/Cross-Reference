package com.dc.crossReference.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.crossReference.dao.GetDistributorDao;
import com.dc.crossReference.entity.Distributor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultGetDistributorService implements GetDistributorService{

  @Autowired
  private GetDistributorDao getDistributorDao;
  
  @Override
  public List<Distributor> getDistributors() {
    log.info("Getting distributors in service");
    
    return getDistributorDao.getDistributors();
  }
}

