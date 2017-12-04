package com.feitianzhu.fu700.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vya on 2017/9/24.
 */

public class TotalScoreModel implements Serializable {

    /**
     * userPoints : {"consumePoints":999919,"merchantPoints":999800,"extendPoints":999900,"bonusPoints":999942,"volunteerPoints":1000000,"partnerPoints":1000000,"distrPoints":1000000,"yellowPearPoints":999990}
     * rebateRecords : {"partnerPointRecord":[{"rebateAmount":5,"rebateDate":"2017-09-22 22:48:29","isIncome":"1"}],"extendPointRecord":[{"rebateAmount":100,"rebateDate":"2017-09-23 12:46:10","isIncome":"0"},{"rebateAmount":100,"rebateDate":"2017-09-23 12:38:06","isIncome":"0"},{"rebateAmount":10,"rebateDate":"2017-09-22 22:47:24","isIncome":"1"}],"yellowPearPointsRecord":[{"rebateAmount":10,"rebateDate":"2017-09-24 15:39:24","isIncome":"0"},{"rebateAmount":8,"rebateDate":"2017-09-22 22:49:04","isIncome":"1"}],"consumePointsRecord":[{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:40:11","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:40:07","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:40:03","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:39:24","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:39:12","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:39:08","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:39:03","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:38:22","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:38:10","isIncome":"1"},{"rebateAmount":0.2,"rebateDate":"2017-09-24 17:38:06","isIncome":"1"}],"volunteerPointRecord":[{"rebateAmount":4,"rebateDate":"2017-09-22 22:48:18","isIncome":"1"}],"bonusPointsRecord":[{"rebateAmount":58,"rebateDate":"2017-09-24 15:45:11","isIncome":"0"},{"rebateAmount":7,"rebateDate":"2017-09-22 22:48:45","isIncome":"1"}],"merchantPointRecord":[{"rebateAmount":200,"rebateDate":"2017-09-24 15:44:47","isIncome":"0"},{"rebateAmount":2,"rebateDate":"2017-09-22 22:48:11","isIncome":"1"}],"distrPointsRecord":[{"rebateAmount":7,"rebateDate":"2017-09-22 22:48:55","isIncome":"1"}]}
     * exchangeRates : [{"type":"1","rate":10},{"type":"2","rate":10},{"type":"3","rate":10},{"type":"4","rate":10},{"type":"5","rate":10},{"type":"6","rate":10},{"type":"7","rate":10},{"type":"8","rate":10}]
     */

    private UserPointsBean userPoints;
    private RebateRecordsBean rebateRecords;
    private List<ExchangeRatesBean> exchangeRates;

    public UserPointsBean getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(UserPointsBean userPoints) {
        this.userPoints = userPoints;
    }

    public RebateRecordsBean getRebateRecords() {
        return rebateRecords;
    }

    public void setRebateRecords(RebateRecordsBean rebateRecords) {
        this.rebateRecords = rebateRecords;
    }

    public List<ExchangeRatesBean> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRatesBean> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public static class UserPointsBean implements Serializable{
        /**
         * consumePoints : 999919
         * merchantPoints : 999800
         * extendPoints : 999900
         * bonusPoints : 999942
         * volunteerPoints : 1000000
         * partnerPoints : 1000000
         * distrPoints : 1000000
         * yellowPearPoints : 999990
         *    "consumePointsBalance": 2433505.66,
         "merchantPointsBalance": 1645.47,
         "extendPointsBalance": 3,
         "bonusPointsBalance": 0,
         "volunteerPointsBalance": 0,
         "partnerPointsBalance": 0,
         "distrPointsBalance": 4.9,
         "yellowPearPointsBalance": 0
         */

        private double consumePoints;
        private double merchantPoints;
        private double extendPoints;
        private double bonusPoints;
        private double volunteerPoints;
        private double partnerPoints;
        private double distrPoints;
        private double yellowPearPoints;

        private double consumePointsBalance;
        private double merchantPointsBalance;
        private double extendPointsBalance;
        private double bonusPointsBalance;
        private double volunteerPointsBalance;
        private double partnerPointsBalance;
        private double distrPointsBalance;
        private double yellowPearPointsBalance;


        public double getConsumePointsBalance() {
            return consumePointsBalance;
        }

        public void setConsumePointsBalance(double consumePointsBalance) {
            this.consumePointsBalance = consumePointsBalance;
        }

        public double getMerchantPointsBalance() {
            return merchantPointsBalance;
        }

        public void setMerchantPointsBalance(double merchantPointsBalance) {
            this.merchantPointsBalance = merchantPointsBalance;
        }

        public double getExtendPointsBalance() {
            return extendPointsBalance;
        }

        public void setExtendPointsBalance(double extendPointsBalance) {
            this.extendPointsBalance = extendPointsBalance;
        }

        public double getBonusPointsBalance() {
            return bonusPointsBalance;
        }

        public void setBonusPointsBalance(double bonusPointsBalance) {
            this.bonusPointsBalance = bonusPointsBalance;
        }

        public double getVolunteerPointsBalance() {
            return volunteerPointsBalance;
        }

        public void setVolunteerPointsBalance(double volunteerPointsBalance) {
            this.volunteerPointsBalance = volunteerPointsBalance;
        }

        public double getPartnerPointsBalance() {
            return partnerPointsBalance;
        }

        public void setPartnerPointsBalance(double partnerPointsBalance) {
            this.partnerPointsBalance = partnerPointsBalance;
        }

        public double getDistrPointsBalance() {
            return distrPointsBalance;
        }

        public void setDistrPointsBalance(double distrPointsBalance) {
            this.distrPointsBalance = distrPointsBalance;
        }

        public double getYellowPearPointsBalance() {
            return yellowPearPointsBalance;
        }

        public void setYellowPearPointsBalance(double yellowPearPointsBalance) {
            this.yellowPearPointsBalance = yellowPearPointsBalance;
        }

        public double getConsumePoints() {
            return consumePoints;
        }

        public void setConsumePoints(double consumePoints) {
            this.consumePoints = consumePoints;
        }

        public double getMerchantPoints() {
            return merchantPoints;
        }

        public void setMerchantPoints(double merchantPoints) {
            this.merchantPoints = merchantPoints;
        }

        public double getExtendPoints() {
            return extendPoints;
        }

        public void setExtendPoints(double extendPoints) {
            this.extendPoints = extendPoints;
        }

        public double getBonusPoints() {
            return bonusPoints;
        }

        public void setBonusPoints(double bonusPoints) {
            this.bonusPoints = bonusPoints;
        }

        public double getVolunteerPoints() {
            return volunteerPoints;
        }

        public void setVolunteerPoints(double volunteerPoints) {
            this.volunteerPoints = volunteerPoints;
        }

        public double getPartnerPoints() {
            return partnerPoints;
        }

        public void setPartnerPoints(double partnerPoints) {
            this.partnerPoints = partnerPoints;
        }

        public double getDistrPoints() {
            return distrPoints;
        }

        public void setDistrPoints(double distrPoints) {
            this.distrPoints = distrPoints;
        }

        public double getYellowPearPoints() {
            return yellowPearPoints;
        }

        public void setYellowPearPoints(double yellowPearPoints) {
            this.yellowPearPoints = yellowPearPoints;
        }
    }

    public static class RebateRecordsBean implements Serializable{
        private List<RebateItemBean> partnerPointRecord;
        private List<RebateItemBean> extendPointRecord;
        private List<RebateItemBean> yellowPearPointsRecord;
        private List<RebateItemBean> consumePointsRecord;
        private List<RebateItemBean> volunteerPointRecord;
        private List<RebateItemBean> bonusPointsRecord;
        private List<RebateItemBean> merchantPointRecord;
        private List<RebateItemBean> distrPointsRecord;
        private List<RebateItemBean> mRebateItem;


        public class  RebateItemBean implements Serializable{
            private double rebateAmount;
            private String rebateDate;
            private String isIncome;

            public double getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(double rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public List<RebateItemBean> getmRebateItem() {
            return mRebateItem;
        }

        public void setmRebateItem(List<RebateItemBean> mRebateItem) {
            this.mRebateItem = mRebateItem;
        }

        public List<RebateItemBean> getPartnerPointRecord() {
            return partnerPointRecord;
        }

        public void setPartnerPointRecord(List<RebateItemBean> partnerPointRecord) {
            this.partnerPointRecord = partnerPointRecord;
        }

        public List<RebateItemBean> getExtendPointRecord() {
            return extendPointRecord;
        }

        public void setExtendPointRecord(List<RebateItemBean> extendPointRecord) {
            this.extendPointRecord = extendPointRecord;
        }

        public List<RebateItemBean> getYellowPearPointsRecord() {
            return yellowPearPointsRecord;
        }

        public void setYellowPearPointsRecord(List<RebateItemBean> yellowPearPointsRecord) {
            this.yellowPearPointsRecord = yellowPearPointsRecord;
        }

        public List<RebateItemBean> getConsumePointsRecord() {
            return consumePointsRecord;
        }

        public void setConsumePointsRecord(List<RebateItemBean> consumePointsRecord) {
            this.consumePointsRecord = consumePointsRecord;
        }

        public List<RebateItemBean> getVolunteerPointRecord() {
            return volunteerPointRecord;
        }

        public void setVolunteerPointRecord(List<RebateItemBean> volunteerPointRecord) {
            this.volunteerPointRecord = volunteerPointRecord;
        }

        public List<RebateItemBean> getBonusPointsRecord() {
            return bonusPointsRecord;
        }

        public void setBonusPointsRecord(List<RebateItemBean> bonusPointsRecord) {
            this.bonusPointsRecord = bonusPointsRecord;
        }

        public List<RebateItemBean> getMerchantPointRecord() {
            return merchantPointRecord;
        }

        public void setMerchantPointRecord(List<RebateItemBean> merchantPointRecord) {
            this.merchantPointRecord = merchantPointRecord;
        }

        public List<RebateItemBean> getDistrPointsRecord() {
            return distrPointsRecord;
        }

        public void setDistrPointsRecord(List<RebateItemBean> distrPointsRecord) {
            this.distrPointsRecord = distrPointsRecord;
        }

        public static class PartnerPointRecordBean implements Serializable {
            /**
             * rebateAmount : 5
             * rebateDate : 2017-09-22 22:48:29
             * isIncome : 1
             */

            private double rebateAmount;
            private String rebateDate;
            private String isIncome;

            public double getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(double rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public static class ExtendPointRecordBean implements Serializable{
            /**
             * rebateAmount : 100
             * rebateDate : 2017-09-23 12:46:10
             * isIncome : 0
             */

            private int rebateAmount;
            private String rebateDate;
            private String isIncome;

            public int getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(int rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public static class YellowPearPointsRecordBean implements Serializable{
            /**
             * rebateAmount : 10
             * rebateDate : 2017-09-24 15:39:24
             * isIncome : 0
             */

            private int rebateAmount;
            private String rebateDate;
            private String isIncome;

            public int getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(int rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public static class ConsumePointsRecordBean implements Serializable{
            /**
             * rebateAmount : 0.2
             * rebateDate : 2017-09-24 17:40:11
             * isIncome : 1
             */

            private double rebateAmount;
            private String rebateDate;
            private String isIncome;

            public double getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(double rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public static class VolunteerPointRecordBean implements Serializable{
            /**
             * rebateAmount : 4
             * rebateDate : 2017-09-22 22:48:18
             * isIncome : 1
             */

            private int rebateAmount;
            private String rebateDate;
            private String isIncome;

            public int getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(int rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public static class BonusPointsRecordBean implements Serializable{
            /**
             * rebateAmount : 58
             * rebateDate : 2017-09-24 15:45:11
             * isIncome : 0
             */

            private int rebateAmount;
            private String rebateDate;
            private String isIncome;

            public int getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(int rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public static class MerchantPointRecordBean implements Serializable{
            /**
             * rebateAmount : 200
             * rebateDate : 2017-09-24 15:44:47
             * isIncome : 0
             */

            private int rebateAmount;
            private String rebateDate;
            private String isIncome;

            public int getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(int rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }

        public static class DistrPointsRecordBean implements Serializable{
            /**
             * rebateAmount : 7
             * rebateDate : 2017-09-22 22:48:55
             * isIncome : 1
             */

            private int rebateAmount;
            private String rebateDate;
            private String isIncome;

            public int getRebateAmount() {
                return rebateAmount;
            }

            public void setRebateAmount(int rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public String getRebateDate() {
                return rebateDate;
            }

            public void setRebateDate(String rebateDate) {
                this.rebateDate = rebateDate;
            }

            public String getIsIncome() {
                return isIncome;
            }

            public void setIsIncome(String isIncome) {
                this.isIncome = isIncome;
            }
        }
    }

    public static class ExchangeRatesBean implements Serializable{
        /**
         * type : 1
         * rate : 10
         */

        private String type;
        private int rate;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }
    }
}
