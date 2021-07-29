package com.springtutorial.springsecurityjpa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springtutorial.springsecurityjpa.daos.ImageRepository;
import com.springtutorial.springsecurityjpa.daos.ProductRepository;
import com.springtutorial.springsecurityjpa.exceptions.ProductExecption;
import com.springtutorial.springsecurityjpa.models.ImageModel;
import com.springtutorial.springsecurityjpa.models.Product;
import com.springtutorial.springsecurityjpa.models.ProductInfo;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ImageRepository imageRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/all")
	public List<ProductInfo> getAll(){
		List<ProductInfo> productInfos = new ArrayList<ProductInfo>();

		List<Product> products = productRepository.findAll();
		
		for(Product product : products) {
			ImageModel img = imageRepository.findById(product.getPicId()).get();
			ProductInfo productInfo = new ProductInfo(img,product);
			productInfos.add(productInfo);
		}
		
		return productInfos;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST, consumes = "multipart/form-data")
	public void  addProduct(@RequestPart("imageFile") MultipartFile file, @RequestPart("product") Product product) throws ProductExecption, IOException{

		System.out.println("Original Image Byte Size - " + file.getBytes().length + " image name: " + file.getOriginalFilename());
		
		ImageModel img = imageRepository.findByName(file.getOriginalFilename()).orElse(null);
					
		if(img == null) {
			img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
					file.getBytes());
			imageRepository.save(img);
		}
		
		Product prd = new Product(product.getId(),product.getName(),product.getDescription(),product.getPrice(),img.getId());
		
		productRepository.save(prd);
		System.out.println("Product: " +prd);
		
	}
	
}
