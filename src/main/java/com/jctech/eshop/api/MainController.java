package com.jctech.eshop.api;

import org.springframework.stereotype.Controller;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller // Dont use RestController as this method is mapping to a static file not a JSON
public class MainController {
	public String index() {
		return "index.html";
	}
}
