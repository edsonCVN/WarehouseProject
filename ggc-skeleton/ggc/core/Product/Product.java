package ggc.core.Product;

//import exceptions

import ggc.core.Notifications;
import ggc.core.Partner;

import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;
import java.text.Collator;

public abstract class Product {
    
	private String _id;
	private float _maxPrice;
	private float _lowestPrice;
	private float _highestPrice;
	private int _currentQuantity;

	public Product(String id, float maxPrice, float lowestPrice, float highestPrice, int currentQuantity) {
		_id = id;
		_maxPrice = maxPrice;
		_lowestPrice = lowestPrice;
		_highestPrice = highestPrice;
		_currentQuantity = currentQuantity;
	}

	public Product (String id) {
		this (id, 0, 0, 0, 0);
	}

	//public abstract float checkQuantity(int quantity, Partner p);

	public String getId() { return _id; }

	public float getMaxPrice() { return _maxPrice; }

	public int getCurrentQuantity() { return _currentQuantity; } 

    @Override
    public String toString() {
        return _id + "|" + _maxPrice + "|" + _highestPrice;
    }

	//public abstract String toString(Product p);
}
