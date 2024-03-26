package it.its.alberghi.entity;

public class Hotel {
    private String name;
        private int price;
        private int suitePrice;

        public Hotel(String name, int price, int suitePrice) {
            this.name = name;
            this.price = price;
            this.suitePrice = suitePrice;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getSuitePrice() {
            return suitePrice;
        }

        @Override
        public String toString() {
            return "Hotel: " + name + ", Price: " + price + ", Suite Price: " + suitePrice;
        }
    
}
