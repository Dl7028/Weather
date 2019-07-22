package cn.eoe.app.entity;

import static cn.eoe.app.ui.CitySearchActivity.airQualitySQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.forecastSQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.wthSQLiteAction;
import static cn.eoe.app.ui.WeatherActivity.k;

/**
 * 我保存的城市的实体类
 * Created by 徐启 on 2019/5/9.
 */

public class MyCities {
    private String cityLocationName;
    private String cityTemperature;
    private String cityQuality;
    private String cityHumidity;
    private String cityWindDirection;
    private String cityWindScale;
    private String cityHigh;
    private String cityLow;
    private String cityText;
    public String cityCode;

  public MyCities(){
      this.cityLocationName = "";
      this.cityTemperature = "";
      this.cityQuality = "";
      this.cityHumidity = "";
      this.cityWindDirection = "";
      this.cityWindScale = "";
      this.cityHigh = "";
      this.cityLow = "";
      this.cityText = "";
      this.cityCode = "";
  }

    public String getCityHigh() {
        return cityHigh;
    }

    public void setCityHigh(String cityHigh) {
        this.cityHigh = cityHigh;
    }

    public String getCityHumidity() {
        return cityHumidity;
    }

    public void setCityHumidity(String cityHumidity) {
        this.cityHumidity = cityHumidity;
    }

    public String getCityLocationName() {
        return cityLocationName;
    }

    public void setCityLocationName(String cityLocationName) {
        this.cityLocationName = cityLocationName;
    }

    public String getCityLow() {
        return cityLow;
    }

    public void setCityLow(String cityLow) {
        this.cityLow = cityLow;
    }

    public String getCityQuality() {
        return cityQuality;
    }


    public void setCityQuality(String cityQuality) {
        this.cityQuality = cityQuality;
    }

    public String getCityTemperature() {
        return cityTemperature;
    }

    public void setCityTemperature(String cityTemperature) {
        this.cityTemperature = cityTemperature;
    }

    public String getCityWindDirection() {
        return cityWindDirection;
    }

    public void setCityWindDirection(String cityWindDirection) {
        this.cityWindDirection = cityWindDirection;
    }

    public String getCityWindScale() {
        return cityWindScale;
    }

    public void setCityWindScale(String cityWindScale) {
        this.cityWindScale = cityWindScale;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
