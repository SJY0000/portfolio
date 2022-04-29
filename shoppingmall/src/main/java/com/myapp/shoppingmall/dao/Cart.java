package com.myapp.shoppingmall.dao;

//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
import lombok.Data;

@Data // get,set,toString 메소드 자동 생성
//@AllArgsConstructor 모든 변수를 포함한 생성자 자동생성
//@NoArgsConstructor 기본 생성자 자동 생성
public class Cart {

	private int id;
	private String name;
	private String price;
	private int quantity;
	private String image;
	
	public Cart(int id, String name, String price, int quantity, String image) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}
	
	
}
