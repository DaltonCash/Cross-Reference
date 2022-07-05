package com.dc.crossReference.controller;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.dc.crossReference.entity.Business;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/upload/business")
public interface UploadBusinessController {
  
  @Operation(
      summary = "Create Database Table for desired business",
      description = "Put in the Instagram and Facebook handles of the desired business. This operation returns all relevant info of the business.",
      responses = {
          @ApiResponse(responseCode = "201", 
              description = "The created business is returned", 
              content = @Content(mediaType = "application/json", 
              schema = @Schema(implementation = Business.class))),
          @ApiResponse(responseCode = "400", 
            description = "The request parameters are invalid", 
            content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", 
            description = "No information was found.", 
            content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", 
            description = "An unplanned error occurred.", 
            content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(name = "fbHandle",
          required = false, 
          allowEmptyValue = true,
          description = "Handle correlating to the business's Facebook page, without the @ symbol."),
         
      }
  )
  
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Business createBusiness(@RequestParam String fbHandle) throws IOException;
}