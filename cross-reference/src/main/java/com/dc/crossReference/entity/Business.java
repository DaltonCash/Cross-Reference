package com.dc.crossReference.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Business {
  private int business_id;
  private String business_name;
  private String business_description;
  private String location;
  private String zip;
  private String fbFollowers;
  private String email;
  private boolean duplicateZipCode;
  private String dateSearched;
  private String additionalMessages;  

}
