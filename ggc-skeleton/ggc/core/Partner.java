package ggc.core;

import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;
import ggc.core.Notifications;

import java.lang.Math;


public class Partner {

  /*  public final static Comparator<Partner> KEY_COMPARATOR =new KeyComparator();

    private static class KeyComparator implements Comparator<Partner>{
        @Override
        public int compare(Partner p1, Partner p2){
            Collator collator = Collator.getInstance(Locale.getDefault());
            return collator.compare(p1.getkey(), p2.getKey());
        }
    }*/
    
    private String _key;
    private String _name;
    private String _address;

    //temos de cria enums para os estatutos
    private String _status;
    private float _points;
    private float _purchases;
    private float _sales;
    private float _paidSales;
    private Notifications _notifications;
    

    public Partner (String key, String name, String address){
        _key = key;
        _name = name;
        _address = address;
        _status = "NORMAL";
        _points = 0;
        _purchases = 0;
        _sales = 0;
        _paidSales = 0;
        _notifications = new Notifications();
    }

    public String getKey() { return _key; }

    @Override
    public String toString() {
        return _key + "|" + _name + "|" + _status + "|" + Math.round (_points) + "|" + Math.round (_purchases) + "|" + Math.round (_sales) + "|" + Math.round (_paidSales);
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Partner) {
            Partner partener = (Partner) o;
            return _key.equals(partener.getKey());
        }
        return false;
    }


}
