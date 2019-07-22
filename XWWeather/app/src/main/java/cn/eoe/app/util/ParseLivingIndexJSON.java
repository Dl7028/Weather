package cn.eoe.app.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.eoe.app.entity.LivingIndex;

/**
 * 解析生活指数api返回的数据
 * Created by 徐启 on 2019/5/7.
 */

public class ParseLivingIndexJSON {
    private String locationName;
    private String acBrief;
    private String carWashingBrief;
    private String dressingBrief;
    private String fishingBrief;
    private String shoppingBrief;
    private String sportBrief;
    private String beerBrief;
    private String umbrellaBrief;
    private String allergyBrief;
    private String uvBrief;
    private String fluBrief;
    private String morningSportBrief;

    LivingIndex livingIndex = new LivingIndex();

    public LivingIndex getLivingIndex(){
        return livingIndex;
    }
    //解析生活指数
    public  void getLivingIndexResult( String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject job = (JSONObject) jsonArray.get(i);
                JSONObject locationObject = job.getJSONObject("location");
                locationName = locationObject.getString("name");
                JSONObject suggestionObject = job.getJSONObject("suggestion");
                JSONObject acObject = suggestionObject.getJSONObject("ac");
                acBrief = acObject.getString("brief");
                JSONObject carWashingObject = suggestionObject.getJSONObject("car_washing");
                carWashingBrief = carWashingObject.getString("brief");
                JSONObject  dressingObject = suggestionObject.getJSONObject("dressing");
                dressingBrief = dressingObject.getString("brief");
                JSONObject  fishingObject = suggestionObject.getJSONObject("fishing");
                fishingBrief = fishingObject.getString("brief");
                JSONObject shoppingObject = suggestionObject.getJSONObject("shopping");
                shoppingBrief = shoppingObject.getString("brief");
                JSONObject sportObject = suggestionObject.getJSONObject("sport");
                sportBrief = sportObject.getString("brief");
                JSONObject beerObject = suggestionObject.getJSONObject("beer");
                beerBrief = beerObject.getString("brief");
                JSONObject umbrellaObject = suggestionObject.getJSONObject("umbrella");
                umbrellaBrief = umbrellaObject.getString("brief");
                JSONObject allergyObject = suggestionObject.getJSONObject("allergy");
                allergyBrief = allergyObject.getString("brief");
                JSONObject uvObject = suggestionObject.getJSONObject("uv");
                uvBrief = uvObject.getString("brief");
                JSONObject fluObject = suggestionObject.getJSONObject("flu");
                fluBrief = fluObject.getString("brief");
                JSONObject morningSportObject = suggestionObject.getJSONObject("morning_sport");
                morningSportBrief = morningSportObject.getString("brief");
                setLivingIndexResult();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //保存生活指数到对象中
    public void  setLivingIndexResult(){
        livingIndex.setLocationName(locationName);
        livingIndex.setAcBrief(acBrief);
        livingIndex.setMorningSportBrief(morningSportBrief);
        livingIndex.setFluBrief(fluBrief);
        livingIndex.setUvBrief(uvBrief);
        livingIndex.setAllergyBrief(allergyBrief);
        livingIndex.setBeerBrief(beerBrief);
        livingIndex.setCarWashingBrief(carWashingBrief);
        livingIndex.setDressingBrief(dressingBrief);
        livingIndex.setShoppingBrief(shoppingBrief);
        livingIndex.setUmbrellaBrief(umbrellaBrief);
        livingIndex.setFishingBrief(fishingBrief);
        livingIndex.setSportBrief(sportBrief);

    }

}
