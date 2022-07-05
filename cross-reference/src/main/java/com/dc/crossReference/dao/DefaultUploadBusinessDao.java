package com.dc.crossReference.dao;

import java.io.IOException;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dc.crossReference.entity.Business;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultUploadBusinessDao implements UploadBusinessDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Business createBusiness(String name, String description, String location, String zip,
      String fbFollowers, String email, boolean inAnotherZipCode, String messages,
      String dateSearched) {

    log.info("Creating business Search. name = {}", name);

    SqlParams params =
        insertSql(name, description, location, zip, fbFollowers, email, inAnotherZipCode, messages);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    int business_id = keyHolder.getKey().intValue();

    return Business.builder().business_id(business_id).business_description(description)
        .business_name(name).location(location).zip(zip).fbFollowers(fbFollowers).email(email)
        .duplicateZipCode(inAnotherZipCode).additionalMessages(messages).dateSearched(dateSearched)
        .build();
  }

  public SqlParams insertSql(String name, String description, String location, String zip,
      String fbFollowers, String email, boolean inAnotherZipCode, String messages) {

    SqlParams params = new SqlParams();

    params.sql = "" + "INSERT INTO business(" + "business_name, business_description, location, "
        + "zip, fb_followers, email, " + "in_another_zip_code, additional_messages" + ") VALUES ("
        + ":business_name, :business_description, :location," + ":zip, :fb_followers, :email, "
        + ":in_another_zip_code, :additional_messages" + ")";

    params.source.addValue("business_name", name);
    params.source.addValue("business_description", description);
    params.source.addValue("location", location);
    params.source.addValue("zip", zip);
    params.source.addValue("fb_followers", fbFollowers);
    params.source.addValue("email", email);
    params.source.addValue("in_another_zip_code", inAnotherZipCode);
    params.source.addValue("additional_messages", messages);

    return params;
  }

  public String fetchHTMLAsString(String url) throws IOException {
    return Jsoup.connect(url).get().toString();
  }

  @Override
  public String findFBLocation(String fbText) {
    // This variable tells the rest of the method what to look for in the HTML String
    String findSubstring = "full_address\":\"";
    String findSubstring2 = "streetAddress\":\"";
    // checks if findSubstring is in the HTML String
    if (fbText.contains(findSubstring)) {
      // gets the index of the findSubstring
      int index = fbText.indexOf(findSubstring) + findSubstring.length();

      String location = "";
      int j = 0;
      int i = 0;
      while (j == 0) {
        // The location category should end in quotes, so once it finds a quote, the while loop
        // terminates. Also checks if the loop has gone on too long
        if (fbText.charAt(index + i) == ('"') || i > 1000) {
          j++;
        } else if (fbText.charAt(index + i) == ('\\')) {
          location += ", ";
          i += 2;

        } else {
          // adds characters to the location string char by char
          location += fbText.charAt(index + i);
          i++;
        }
      }
      return location;
    }
    if (fbText.contains(findSubstring2)) {
      // gets the index of the findSubstring
      int index = fbText.indexOf(findSubstring2) + findSubstring2.length();

      String followers = "";
      int j = 0;
      int i = 0;
      while (j < 4) {
        // The follower category should end in a quote, so once it finds a quote, the while loop
        // terminates. Also checks if the loop has gone on too long
        if (fbText.charAt(index + i) == ('"') && j == 0) {
          j++;
          i += 21;
          followers += " ";
        } else if (fbText.charAt(index + i) == '"' && j == 1) {
          j++;
          i += 19;
          followers += " ";
        } else if (fbText.charAt(index + i) == '"' && j == 2) {
          j++;
          i += 16;
          followers += " ";
        } else if (fbText.charAt(index + i) == '"' && j == 3) {
          j++;
        } else {
          // adds characters to the follower string char by char
          followers += fbText.charAt(index + i);
          i++;
        }
      }
      return followers;
    }
    return "";
  }

  @Override
  public String findFBFollowers(String fbText) {
    // This variable tells the rest of the method what to look for in the HTML String
    String findSubstring = ",\"follower_count\":";
    String findSubstring2 = "people follow this";
    // checks if findSubstring is in the HTML String
    if (fbText.contains(findSubstring)) {
      // gets the index of the findSubstring
      int index = fbText.indexOf(findSubstring) + findSubstring.length();

      String followers = "";
      int j = 0;
      int i = 0;
      while (j == 0) {
        // The follower category should end in a quote, so once it finds a quote, the while loop
        // terminates.
        // Also checks if the loop has gone on too long
        if (fbText.charAt(index + i) == ('"') || i > 1000) {
          j++;
        } else {
          // adds characters to the follower string char by char
          followers += fbText.charAt(index + i);
          i++;
        }
      }
      // removes the comma at the end of the string
      return followers.substring(0, followers.length() - 1);
    }
    // checks if findSubstring2 is in the Jsoup String
    if (fbText.contains(findSubstring2)) {
      // gets the index of the findSubstring
      int index = fbText.indexOf(findSubstring2) - 2;

      String followers = "";
      int j = -1;
      int i = 0;
      // 'rewinds the tape' so to speak of the specified index to include the number of followers.
      // This is necessary because findSubstring2 appears after the desired number
      while (j == -1) {
        if (Pattern.matches("\\d", fbText.substring(index + i, index + i + 1)) == false
            && fbText.charAt(index + i) == (',') == false) {
          j++;
          i++;
        } else {
          i--;
        }
      }
      while (j == 0) {
        // The follower category should end in a space, so once it finds a space, the while loop
        // terminates. Also checks if the loop has gone on too long to prevent infinite loop.
        if (fbText.charAt(index + i) == (' ') || i > 1000) {
          j++;
        } else {
          // adds characters to the follower string char by char
          followers += fbText.charAt(index + i);
          i++;
        }
      }
      return followers;
    }
    return "";
  }

  @Override
  public String findFBEmail(String fbText) {
    // This variable tells the rest of the method what to look for in the HTML String
    String findSubstring = "\"email\":{\"text\":\"";
    // checks if findSubstring is in the HTML String
    if (fbText.contains(findSubstring)) {
      int index = fbText.indexOf(findSubstring) + findSubstring.length();

      String email = "";
      int j = 0;
      int i = 0;
      while (j == 0) {
        // The business email should end in a quote, so once it finds a quote, the while loop terminates.
        // Also checks if the loop has gone on too long
        if (fbText.charAt(index + i) == ('"')) {
          j++;
        } else if (fbText.charAt(index + i) == ('\\')) {
          // this check is put here to change the unicode '\u0040' to the @ symbol
          email += "@";
          i += 6;
        } else {
          // adds characters to the email string char by char
          email += fbText.charAt(index + i);
          i++;
        }
      }
      return email;
    }
    return "";
  }

  @Override
  public String findFBProducts(String fbText) {
    // This variable tells the rest of the method what to look for in the HTML String
    String findSubstring = ",\"category_name\":\"";
    String findSubstring2 = "/pages/category/";
    // checks if findSubstring is in the HTML String
    if (fbText.contains(findSubstring)) {
      // gets the index of the findSubstring
      int index = fbText.indexOf(findSubstring) + findSubstring.length();

      String products = "";
      int j = 0;
      int i = 0;
      while (j == 0) {
        // The shop category should end in a quote, so once it finds a quote, the while loop
        // terminates.
        // Also checks if the loop has gone on too long
        if (fbText.charAt(index + i) == ('"') || i > 1000) {
          j++;
        } else {
          // adds characters to the product category string char by char
          products += fbText.charAt(index + i);
          i++;
        }
      }
      return products;
    }
    String products = "";
    int x = 0;
    while (x == 0) {
      if (fbText.contains(findSubstring2)) {
        // gets the index of the findSubstring
        int index = fbText.indexOf(findSubstring2) + findSubstring2.length();


        int j = 0;
        int i = 0;
        while (j == 0) {
          // The shop category should end in a quote, so once it finds a quote, the while loop
          // terminates. Also checks if the loop has gone on too long
          if (fbText.charAt(index + i) == ('"') || i > 1000) {
            j++;
            fbText = fbText.substring(index + i);
          } else {
            // adds characters to the product category string char by char
            products += fbText.charAt(index + i);
            i++;

          }
        }
      } else {
        x++;
      }
    }
    return (products.isEmpty()) ? "" : products.substring(0, products.length() - 1);
  }

  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }

  @Override
  public String findFBProductsAgain(String fbText) {
    String findSubstring = ",\"description\":{\"text\":";
    // checks if findSubstring is in the HTML String
    if (fbText.contains(findSubstring)) {
      // gets the index of the findSubstring
      int index = fbText.indexOf(findSubstring) + findSubstring.length();

      String products = "";
      int j = 0;
      int i = 0;
      while (j == 0) {
        // The shop category should end in an end brace, so once it finds a quote, the while loop
        // terminates. Also checks if the loop has gone on too long
        if (fbText.charAt(index + i) == ('}') || i > 1000) {
          j++;
        } else {
          // adds characters to the product category string char by char
          products += fbText.charAt(index + i);
          i++;
        }
      }
      return products;
    }
    return "";
  }
}
