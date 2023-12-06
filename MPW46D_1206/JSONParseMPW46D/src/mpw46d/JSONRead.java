package mpw46d;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;

public class JSONRead {

    static String line="",str="";
    public static void main(String[] args) throws JSONException, IOException {
        String link = "MPW46D_1206/JSONParseMPW46D/MPW46D_kurzusfelvetel.xml";
        BufferedReader br = new BufferedReader(new FileReader(link));
        while ((line = br.readLine()) != null) 
        {   
            str+=line;  
        }
        JSONObject jsondata = XML.toJSONObject(str);
        System.out.println(jsondata);
        br.close();
    }

}