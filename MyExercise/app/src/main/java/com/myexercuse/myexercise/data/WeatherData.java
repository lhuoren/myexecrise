package com.myexercuse.myexercise.data;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public class WeatherData {

    /**
     * basic : {"city":"深圳","cnty":"中国","id":"CN101280601","lat":"22.544000","lon":"114.109000",
     * "update":{"loc":"2016-12-02 15:51","utc":"2016-12-02 07:51"}}
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"25","hum":"55","pcpn":"0","pres":"1021",
     * "tmp":"23","vis":"6","wind":{"deg":"40","dir":"西风","sc":"3-4","spd":"12"}}
     * status : ok
     */

    public List<HeWeather5Bean> HeWeather5;

    public static class HeWeather5Bean {
        /**
         * city : 深圳
         * cnty : 中国
         * id : CN101280601
         * lat : 22.544000
         * lon : 114.109000
         * update : {"loc":"2016-12-02 15:51","utc":"2016-12-02 07:51"}
         */

        public BasicBean basic;
        /**
         * cond : {"code":"101","txt":"多云"}
         * fl : 25
         * hum : 55
         * pcpn : 0
         * pres : 1021
         * tmp : 23
         * vis : 6
         * wind : {"deg":"40","dir":"西风","sc":"3-4","spd":"12"}
         */

        public NowBean now;
        public String status;

        public static class BasicBean {
            public String city;
            public String cnty;
            public String id;
            public String lat;
            public String lon;
            /**
             * loc : 2016-12-02 15:51
             * utc : 2016-12-02 07:51
             */

            public UpdateBean update;

            public static class UpdateBean {
                public String loc;
                public String utc;
            }
        }

        public static class NowBean {
            /**
             * code : 101
             * txt : 多云
             */

            public CondBean cond;
            public String fl;
            public String hum;
            public String pcpn;
            public String pres;
            public String tmp;
            public String vis;
            /**
             * deg : 40
             * dir : 西风
             * sc : 3-4
             * spd : 12
             */

            public WindBean wind;

            public static class CondBean {
                public String code;
                public String txt;
            }

            public static class WindBean {
                public String deg;
                public String dir;
                public String sc;
                public String spd;
            }
        }
    }
}
