package com.dc.crossReference.dao;

import com.dc.crossReference.entity.Distributor;

public interface UploadDistributorDao {

  Distributor createDistributor(String distributor_name, String location, String zip);

}
