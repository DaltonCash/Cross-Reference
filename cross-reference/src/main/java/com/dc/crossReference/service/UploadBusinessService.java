package com.dc.crossReference.service;

import java.io.IOException;
import com.dc.crossReference.entity.Business;

public interface UploadBusinessService {

  Business createBusiness(String instaHandle) throws IOException;

}
