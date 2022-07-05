package com.dc.crossReference.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.crossReference.dao.GetBusinessDao;
import com.dc.crossReference.entity.Business;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultGetBusinessService implements GetBusinessService{

  @Autowired
  private GetBusinessDao getBusinessDao;
  
  @Override
  public List<Business> getBusinesses() {
    log.info("Getting businesses in service");
    
    return getBusinessDao.getBusinesses();
  }
}
