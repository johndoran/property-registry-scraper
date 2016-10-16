package com.jd.propertyscraper

import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
public class PropertyController {

    private static  final String URL = "https://www.propertypriceregister.ie/Website/npsra/PPR/npsra-ppr.nsf/PPR-By-Date?SearchView&Start=1&SearchMax=0&SearchOrder=4&Query=[dt_execution_date]>=01/01/2016 AND [dt_execution_date]<01/7/2016 AND [dc_county]=\"Dublin\"&County=Dublin&Year=2016&StartMonth=01&EndMonth=06&Address="

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Property> properties() {
        buildPropertyList(restTemplate.getForEntity(URL, String.class))
    }

    private List<Property> buildPropertyList(String response) {
        def doc = Jsoup.parse(response)

        def properties = []

        doc.getElementsByClass("resultsTable")[0]?.getElementsByTag("tbody")[0]?.getElementsByTag("tr").each { link ->
            def property = new Property()
            link.getElementsByTag("td").eachWithIndex { Element tdElement, int index ->
                if (index == 0) {
                    property.date = JSONObject.quote(tdElement.text())
                } else if (index == 1) {
                    property.price = tdElement.text()
                } else {
                    property.address = JSONObject.quote(tdElement.text())
                }
            }
            properties.add(property)
        }

        properties
    }

}
