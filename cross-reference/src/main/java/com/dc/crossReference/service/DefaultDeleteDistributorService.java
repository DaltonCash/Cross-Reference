package com.dc.crossReference.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.crossReference.dao.DeleteDistributorDao;
import com.dc.crossReference.entity.Distributor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultDeleteDistributorService implements DeleteDistributorService {
  
  @Autowired
  private DeleteDistributorDao deleteDistributorDao;
  
  public Distributor deleteDistributor(int distributor_id) {
    log.info("Request for the deletion of distributor_id = {} has been picked up in service", distributor_id);
    Distributor distributor = deleteDistributorDao.fetchDistributor(distributor_id);
    
    return deleteDistributorDao.deleteDistributor(distributor_id, distributor);
  }
}
