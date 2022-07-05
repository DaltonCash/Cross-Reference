package com.dc.crossReference.dao;

import java.io.IOException;
import com.dc.crossReference.dao.DefaultUploadBusinessDao.SqlParams;
import com.dc.crossReference.entity.Business;

public interface UploadBusinessDao {

  String fetchHTMLAsString(String url) throws IOException;

  Business createBusiness(String name, String description, String location, String zip,
      String fbFollowers, String email, boolean inAnotherZipCode, String messages,
      String dateSearched);

  SqlParams insertSql(String name, String description, String location, String zip,
      String fbFollowers, String email, boolean inAnotherZipCode, String messages);

  String findFBLocation(String fbText);

  String findFBFollowers(String fbText);

  String findFBEmail(String fbText);

  String findFBProducts(String fbText);

  String findFBProductsAgain(String fbText);

}
