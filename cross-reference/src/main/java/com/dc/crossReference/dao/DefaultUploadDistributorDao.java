package com.dc.crossReference.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dc.crossReference.entity.Distributor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultUploadDistributorDao implements UploadDistributorDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public Distributor createDistributor(String distributor_name, String location, String zip) {
    
    log.info("creating new distributor. name = {}", distributor_name);
    
    SqlParams params = insertSql(distributor_name, location, zip);
    
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);
    
    int distributor_id = keyHolder.getKey().intValue();
    
    return Distributor.builder()
          .distributor_id(distributor_id)
          .distributor_name(distributor_name)
          .location(location)
          .zip(zip)
          .build();
  }
  
  private SqlParams insertSql(String distributor_name, String location, String zip) {
    SqlParams params = new SqlParams();
    
    params.sql = ""
        + "INSERT INTO distributor("
        + "distributor_name, location, zip"
        + ") VALUES ("
        + ":distributor_name, :location, :zip"
        + ")";
 
    params.source.addValue("distributor_name", distributor_name);
    params.source.addValue("location", location);
    params.source.addValue("zip", zip);
    
    return params;
  }

  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }
}
