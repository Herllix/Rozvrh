package cz.uhk;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class RozvrhApi {
    private final Gson gson = new Gson();

    public List<RozvrhovaAkce> fetchScheduleData(String urlString){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())){
                    StagRozvrhResponse response = gson.fromJson(reader, StagRozvrhResponse.class);
                    if(response != null && response.getRozvrhovaAkce() != null){
                        return response.getRozvrhovaAkce();
                    }else{
                        System.out.println("Chyba: JSON odpověď je prázdná nebo má jinou strukturu");
                        return Collections.emptyList();
                    }
                }catch(JsonSyntaxException e){
                    System.err.println("Chyba parsování JSONu: " + e.getMessage());
                    return Collections.emptyList();
                }
            }else{
                System.err.println("Chyba serveru HTTP" + responseCode + " " + connection.getResponseMessage());
                return Collections.emptyList();
            }
        }catch(MalformedURLException e){
            System.err.println("Chyba: neplatná URL " + urlString);
            return Collections.emptyList();
        }catch (IOException e){
            System.err.println("Chyba serveru IO" + e.getMessage());
            return Collections.emptyList();
        }finally{
            if(connection != null){
                connection.disconnect();
            }
        }
    }
}
