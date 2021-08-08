package com.example.lap_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ViewActivity extends AppCompatActivity {
    int mode;
    TextView xml, json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        xml = findViewById(R.id.xmlPlaceHolder);
        json = findViewById(R.id.jsonPlaceHolder);

        mode = getIntent().getIntExtra("mode", 0);

        if (mode == 1) {
            parseXML();
        }
        if (mode == 2) {
            parseJSON();
        }
    }

    public void parseJSON() {
        String stringData;
        try {
            InputStream inputStream = getAssets().open("input.json");
            int size = inputStream.available();
            byte buffer[] = new byte[size];
            inputStream.read(buffer);
            stringData = new String(buffer);
            JSONObject jsonObject = new JSONObject(stringData);
            JSONObject cityObject = jsonObject.getJSONObject("City");
            String cityName = cityObject.getString("City-Name");
            String longitude = cityObject.getString("Longitude");
            String latitude = cityObject.getString("Latitude");
            String temperature = cityObject.getString("Temperature");
            String humidity = cityObject.getString("Humidity");
            json.setText("City : " + cityName + "\n");
            json.append("Longitude : " + longitude + "\n");
            json.append("Latitude : " + latitude + "\n");
            json.append("Temperature : " + temperature + "\n");
            json.append("Humidity : " + humidity + "\n");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseXML(){

        xml.setText(" ");




        try {
            InputStream inputStream = getAssets().open("input.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(inputStream);

            NodeList cityList = document.getElementsByTagName("City");

            for(int i=0; i<cityList.getLength();i++){

                Node c = cityList.item(i);

                if(c.getNodeType() == Node.ELEMENT_NODE){
                    Element city = (Element) c;

                    // String id = city.getAttribute("id");
                    // Log.e("data", "parseXML: "+id );

                    NodeList cityDetailList = city.getChildNodes();

                    for(int j=0;j<cityDetailList.getLength();j++) {
                        Node n = cityDetailList.item(j);

                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element cityDetail = (Element) n;


                            String tagValue = cityDetail.getTagName();
                            String value = cityDetail.getTextContent();

                            xml.append(tagValue +" : "+value + "\n");

                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


    }

}