package com.dc.crossReference.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.dc.crossReference.entity.Distributor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultGetDistributorDao implements GetDistributorDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public List<Distributor> getDistributors() {
    log.info("Request has been sent for all distributors in dao");
    String sql = "SELECT * FROM distributor "
               + "ORDER BY distributor_name ASC"; 
    
    return jdbcTemplate.query(sql, new RowMapper<Distributor>(){

      @Override
      public Distributor mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        return Distributor.builder()
                          .distributor_id(rs.getInt("distributor_id"))
                          .distributor_name(rs.getString("distributor_name"))
                          .location(rs.getString("location"))
                          .zip(rs.getString("zip"))
                          .build();
      }
    });
  }
}