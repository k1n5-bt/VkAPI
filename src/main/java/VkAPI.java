import org.json.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class VkAPI {
    public static void main(String[] args) throws IOException, JSONException {
        HashMap<String, Integer> educations = new HashMap<>();
        //API запрос с полем education
        String urlApi = String.format("%s%s%s",
                "https://api.vk.com/method/groups.getMembers",
                "?group_id=iritrtf_urfu&fields=education&access_token=",
                "3b8c43d93b8c43d93b8c43d9f33bf8c0a133b8c3b8c43d96433c38b4ed97e268535f27e&v=5.126");
        URL url = new URL(urlApi);
        URLConnection urlConnection = url.openConnection();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String result = buffer.readLine();
        buffer.close();
        JSONObject peopleJSON = new JSONObject(result);
        JSONArray arrayPeople = peopleJSON.getJSONObject("response").getJSONArray("items");
        //Через API запрос мы собираем данные о уровне образования у всех подписчиков группы и вбиваем его в словарь, чтобы узнать кол-во каждого уровня образования
        for(int i=0; i < arrayPeople.length(); i++) {
            JSONObject info = arrayPeople.getJSONObject(i);
            try {
                String education = info.get("education_status").toString();
                educations.put(education, educations.containsKey(education) ? educations.get(education) + 1 : 1);
            } catch (Exception e) { }
        }
        //Выводим данные
        for(Map.Entry<String, Integer> item : educations.entrySet()) {
            System.out.println(item.getKey() +": " + item.getValue());
        }
    }
}
