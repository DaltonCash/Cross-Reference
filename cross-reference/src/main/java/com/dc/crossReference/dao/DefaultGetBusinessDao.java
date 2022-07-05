package com.dc.crossReference.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.dc.crossReference.entity.Business;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultGetBusinessDao implements GetBusinessDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public List<Business> getBusinesses() {
    log.info("Request has been sent for all searched businesses in dao");
    String sql = "SELECT * FROM business "
               + "ORDER BY business_id desc"; 
    
    return jdbcTemplate.query(sql, new RowMapper<Business>(){

      @Override
      public Business mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        return Business.builder()
            .business_id(rs.getInt("business_id"))
            .business_name(rs.getString("business_name"))
            .business_description(rs.getString("business_name"))
            .location(rs.getString("location"))
            .zip(rs.getString("zip"))
            .fbFollowers(rs.getString("fb_followers"))
            .email(rs.getString("email"))
            .duplicateZipCode(rs.getBoolean("in_another_zip_code"))
            .dateSearched(rs.getString("date_searched"))
            .additionalMessages(rs.getString("additional_messages"))
            .build();
      }
    });
  }
}