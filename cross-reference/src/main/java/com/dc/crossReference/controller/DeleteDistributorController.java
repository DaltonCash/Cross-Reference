package com.dc.crossReference.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dc.crossReference.entity.Distributor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/delete/distributor")
public interface DeleteDistributorController {
  
  @Operation(
      summary = "Delete distributor",
      description = "Delete distributor you used to work with by using the distributor's id",
      responses = {
          @ApiResponse(responseCode = "200", 
              description = "Distributor deleted successfully!", 
              content = @Content(mediaType = "application/json", 
              schema = @Schema(implementation = Distributor.class))),
          @ApiResponse(responseCode = "400", 
            description = "The request parameters are invalid", 
            content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
            description = "Couldn't find a distributor with that ID.", 
            content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
            description = "An unplanned error occurred.", 
            content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(name = "distributor_id",
            required = true, 
            description = "id of the distributor you are no longer doing business with. Check the "
                + "'GetDistributorController' to look through distributors for the distributor_id."),
      }
  )
  
  @DeleteMapping
  @ResponseStatus(code = HttpStatus.OK)
  Distributor deleteDistributor(@RequestParam int distributor_id);
}      