package com.takeaway.gameofthree.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player implements Serializable {

	private static final long serialVersionUID = 3126492436804902667L;

	private Long id;
	
	private Integer number;
	
}
