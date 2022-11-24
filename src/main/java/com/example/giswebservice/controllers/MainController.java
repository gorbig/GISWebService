package com.example.giswebservice.controllers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String indexPage(Model model) throws IOException, InterruptedException, ApiException {

        String searchQuery = "iphone 13";

        // Instantiate the client
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        // Set up the URL with the search term and send the request
        String searchUrl = "https://shop.kz/smartfony/filter/astana-is-v_nalichii-or-ojidaem-or-dostavim/apply/";
        HtmlPage page = client.getPage(searchUrl);

        List<HtmlElement> items = page.getByXPath("//div[@class='bx_catalog_item']");
        if (!items.isEmpty()) {
            for (HtmlElement item : items) {

                HtmlElement titleElement = item.getFirstByXPath(".//div[@class='bx_catalog_item_title']/a");

                HtmlElement imageElement = item.getFirstByXPath(".//div[@class='item_image_container']/a");

                HtmlElement priceElement = item.getFirstByXPath(".//div[@class='bx_catalog_item_price']/div");

                String itemName = titleElement.getAttribute("title");
                String itemUrl = titleElement.getAttribute("href");

                System.out.println( String.format("Name : %s| ItemURL: %s", itemName, itemUrl));

            }
        }
        else {
            System.out.println("No items found !");
        }

        return "index";
    }

    @GetMapping(value = "/map")
    public String mapPage(Model model) throws IOException, InterruptedException, ApiException {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAfObxTmMX5FhKclsn6cwMt5AOtyX0S_Pg")
                .build();
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                "Turkistan Street, Astana 020000").await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(results[0].addressComponents));

        return "index";
    }
}
