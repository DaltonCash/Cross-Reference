package com.dc.crossReference.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Distributor {

  private int distributor_id;
  private String distributor_name;
  private String location; 
  private String zip; 
  
}
