package Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Helper {
    public static ArrayList<User> getUsers(String TAG) throws Exception {
        String pre1 = "https://www.codechef.com/api/rankings/";
        String pre2 = "?sortBy=user_handle&order=asc&page=";
        String pre3 = "&itemsPerPage=100";
        JSONObject response = new JSONObject(getJSOnRESPONSE(pre1 + TAG + pre2 + "1" + pre3));
        Integer pages = response.getInt("availablePages");
        ArrayList<User> arrayList = new ArrayList<>();
        for (int i = 1; i <= pages; i++) {
            System.out.println(i + " done");
            arrayList.addAll(parseUsers(new JSONObject(getJSOnRESPONSE(pre1 + TAG + pre2 + String.valueOf(i) + pre3))));
        }
        return arrayList;

    }

    private static ArrayList<User> parseUsers(JSONObject jsonObject) throws Exception {
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        ArrayList<User> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            User temp = new User();
            temp.name = jsonObject1.getString("name");
            temp.user_handle = jsonObject1.getString("user_handle");
            temp.total_time = jsonObject1.getString("total_time");
            temp.rating = jsonObject1.getString("rating");
            temp.score = jsonObject1.getInt("score");
            temp.penalty = jsonObject1.getInt("penalty");
            temp.rank = jsonObject1.getInt("rank");
            arrayList.add(temp);
        }
        return arrayList;

    }

    private static String getJSOnRESPONSE(String URL) throws Exception {
        java.net.URL url = new URL(URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setInstanceFollowRedirects(false);
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-Type", "application/json");


        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String temp;
        StringBuffer jsonResponse = new StringBuffer();
        while ((temp = br.readLine()) != null) jsonResponse.append(temp);
        urlConnection.disconnect();
        return jsonResponse.toString();
    }

}
