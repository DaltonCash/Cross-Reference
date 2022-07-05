package com.dc.crossReference.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dc.crossReference.dao.GetDistributorDao;
import com.dc.crossReference.dao.UploadBusinessDao;
import com.dc.crossReference.entity.Business;
import com.dc.crossReference.entity.Distributor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultUploadBusinessService implements UploadBusinessService {
  
  @Autowired
  private UploadBusinessDao uploadBusinessDao;

  @Autowired
  private GetDistributorDao getDistributorDao;
  
  @Override
  public Business createBusiness( String fbHandle) throws IOException {
    log.info("Creating business in service, FBHandle = {}", fbHandle);
    
    String fbText = uploadBusinessDao.fetchHTMLAsString("https://www.facebook.com/" + fbHandle + "/");
    String fbTextAbout = uploadBusinessDao.fetchHTMLAsString("https://www.facebook.com/" + fbHandle + "/about");
    
    String name = fbHandle;
    
    String location = uploadBusinessDao.findFBLocation(fbText);
    String zip = "";
    if(location.isEmpty() == false) {
      zip = location.substring(location.length() - 5);
    }
    
    String description = uploadBusinessDao.findFBProducts(fbText);
    if(description.equals(name) || description.isEmpty()) {
      description.equals(uploadBusinessDao.findFBProductsAgain(fbTextAbout));
    }
    
    String fbFollowers = uploadBusinessDao.findFBFollowers(fbText);
    
    String email = uploadBusinessDao.findFBEmail(fbText);
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String dateSearched = LocalDateTime.now().format(formatter);
    
    List<Distributor>  distributors = getDistributorDao.getDistributors();
    
    boolean inAnotherZipCode = false;
    String messages = "";
    for(Distributor distributor : distributors) {
      if(zip.equals(distributor.getZip())) {
        inAnotherZipCode = true;
      }
      
      if(name.equals(distributor.getDistributor_name())) {
        messages += "Duplicate name with distributor: name = " 
                 + distributor.getDistributor_name() 
                 + ", ID = "
                 + distributor.getDistributor_id()
                 + ". ";
                  
      }
      
      if(zip.equals(distributor.getZip())) {
        messages += "Duplicate Zip Code with distributor: name = "
                 + distributor.getDistributor_name()
                 + ", ID = "
                 + distributor.getDistributor_id()
                 + ". ";
      }
    }
    
    return uploadBusinessDao.createBusiness(name, description, location, zip, fbFollowers, email, inAnotherZipCode, messages, dateSearched);
  }
}
