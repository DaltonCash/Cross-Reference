package com.dc.crossReference.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dc.crossReference.entity.Distributor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/upload/distributor")
public interface UploadDistributorController {
  
  @Operation(
      summary = "Create Database Table for desired distributor",
      description = "Input required fields to add a distributor to the database.",
      responses = {
          @ApiResponse(responseCode = "201", 
              description = "The created distributor is returned", 
              content = @Content(mediaType = "application/json", 
              schema = @Schema(implementation = Distributor.class))),
          @ApiResponse(responseCode = "400", 
            description = "The request parameters are invalid", 
            content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
            description = "An unplanned error occurred.", 
            content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(name = "distributor_name",
            required = true, 
            allowEmptyValue = false,
            description = "Name of business. (i.e, 'Jane Dane Jewelry') "),
          @Parameter(name = "location",
            required = false, 
            allowEmptyValue = true,
            description = "Current location of Distributor. (If store has multiple locations, "
                + "make another distributor entry with another location.)"),
          @Parameter(name = "zip",
            required = false,
            allowEmptyValue = true,
            description = "Zip code of business location"),
      }
  )
  
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Distributor createDistributor(
      @RequestParam String distributor_name,
      @RequestParam String location,
      @RequestParam String zip);
}      