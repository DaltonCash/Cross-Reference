package com.dc.crossReference.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dc.crossReference.entity.Distributor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultDeleteDistributorDao implements DeleteDistributorDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public Distributor deleteDistributor(int distributor_id, Distributor distributor) {
    
    log.info("Request has been sent to delete distributor_id = {} in dao", distributor_id);
    SqlParams params = deleteSql(distributor_id);
    
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);
 
    return Distributor.builder()
                      .distributor_id(distributor.getDistributor_id())
                      .distributor_name(distributor.getDistributor_name())
                      .location(distributor.getLocation())
                      .zip(distributor.getZip())
                      .build();
    }
    
  public SqlParams deleteSql(int distributor_id) {
    SqlParams params = new SqlParams();
    
    params.sql = ""
        + "DELETE FROM distributor "
        + "WHERE distributor_id "
        + "= :distributor_id";
 
    params.source.addValue("distributor_id", distributor_id);
    
    return params;
  }

  public Distributor fetchDistributor(int distributor_id){

    String sql = "" 
        + "SELECT * "
        + "FROM distributor "
        + "WHERE distributor_id = :distributor_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("distributor_id", distributor_id);
   
    return jdbcTemplate.query(sql, params, new StockResultSetExtractor());
  }
  
  class StockResultSetExtractor implements ResultSetExtractor<Distributor> {
    @Override
    public Distributor extractData(ResultSet rs) throws SQLException {
      rs.next();
      return Distributor.builder()
                         .distributor_id(rs.getInt("distributor_id"))
                         .distributor_name(rs.getString("distributor_name"))
                         .location(rs.getString("location"))
                         .zip(rs.getString("zip"))
                         .build();
    }
  }

  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }
}