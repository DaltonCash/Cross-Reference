package com.dc.crossReference.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dc.crossReference.entity.Business;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/get/business")
public interface GetBusinessController {
  
  @Operation(
      summary = "Get Businesses.",
      description = "Get Businesses you've previously searched for.",
      responses = {
          @ApiResponse(responseCode = "200", 
              description = "The searched businesses are returned", 
              content = @Content(mediaType = "application/json", 
              schema = @Schema(implementation = Business.class))),
          @ApiResponse(responseCode = "500", 
            description = "An unplanned error occurred.", 
            content = @Content(mediaType = "application/json"))
      }
  )
  
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Business> getBusinesses();
}      