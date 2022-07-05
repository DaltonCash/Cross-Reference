package com.dc.crossReference.service;

import com.dc.crossReference.entity.Distributor;

public interface UploadDistributorService {

  Distributor createDistributor(String distributor_name, String location, String zip);

}
