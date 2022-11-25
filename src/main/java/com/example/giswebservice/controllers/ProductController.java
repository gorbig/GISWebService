package com.example.giswebservice.controllers;

import com.example.giswebservice.entities.Product;
import com.example.giswebservice.services.ProductService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Эта аннотация применяется к классу, чтобы пометить его как обработчик запросов.
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products/parse")
    public String parseSmartphones() throws IOException, InterruptedException, ApiException {

        //Создаем экземпляр клиента
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        //Настроить URL-адрес с условием поиска и отправьте запрос
        String siteUrl = "https://kz.e-katalog.com";
        String category = "katalog_=122&presets_=1358";
        String listUrl = "/ek-list.php?";
        String searchUrl = siteUrl + listUrl + category;

        HtmlPage page = client.getPage(searchUrl);
        HtmlElement paginationElement = page.getFirstByXPath("//div[@class='ib page-num']");
        HtmlElement lastPage = (HtmlElement) paginationElement.getLastElementChild();

        //Берем макс. количество стр.
        int maxPages = Integer.parseInt(lastPage.asNormalizedText());

        ArrayList<Product> products = new ArrayList<>();

        for (int i = 0; i <= maxPages; i++ ){
            String pageNum = "&page=" + i;
            page = client.getPage(searchUrl + pageNum);

            //Находим все элементы товаров на странице по классу
            List<HtmlElement> items = page.getByXPath("//div[@class='model-short-div list-item--goods   ']");
            if (!items.isEmpty()) {

                //Перебираем все элементы
                for (HtmlElement item : items) {

                    HtmlAnchor titleAnchor = item.getFirstByXPath("//a[@class='model-short-title no-u']");
                    HtmlElement descrElement = item.getFirstByXPath(".//div[@class='model-short-description']/div[@class='m-s-f2']");
                    HtmlElement imgElement = item.getFirstByXPath(".//div[@class='list-img h']/img");
                    List<HtmlElement> priceTable = item.getByXPath(".//table[@class='model-hot-prices']/tbody/tr");

                    String itemName = titleAnchor.asNormalizedText();
                    String itemDescr = descrElement.asNormalizedText();
                    String itemImg = siteUrl + imgElement.getAttribute("src");
                    String itemUrl = siteUrl + titleAnchor.getHrefAttribute();

                    for(HtmlElement shopPriceElement: priceTable){
                        Product product = new Product();

                        HtmlElement shopElement = shopPriceElement.getFirstByXPath(".//td[@class='model-shop-name']/div/a/u");
                        HtmlElement priceElement = shopPriceElement.getFirstByXPath(".//td[@class='model-shop-price']/a");
                        String shopName = shopElement.asNormalizedText();
                        String productPrice = priceElement.asNormalizedText();

                        product.setName(itemName);
                        product.setDescription(itemDescr);
                        product.setImg_url(itemImg);
                        product.setPage_url(itemUrl);
                        product.setCategory("Smartphones");
                        product.setShop_name(shopName);
                        product.setPrice(productPrice);

                        products.add(productService.updateProduct(product));
                    }
                }
            }
        }

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.toJson(products);
        return "Products parsed: " + products.size();
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
