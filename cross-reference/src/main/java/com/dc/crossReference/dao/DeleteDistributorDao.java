package com.dc.crossReference.dao;

import com.dc.crossReference.dao.DefaultDeleteDistributorDao.SqlParams;
import com.dc.crossReference.entity.Distributor;

public interface DeleteDistributorDao {

  Distributor deleteDistributor(int distributor_id, Distributor distributor);

  Distributor fetchDistributor(int distributor_id);
  
  SqlParams deleteSql(int distributor_id);
  
}
