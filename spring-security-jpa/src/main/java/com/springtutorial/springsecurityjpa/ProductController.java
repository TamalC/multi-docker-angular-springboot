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
	
	//Map<Integer, ProductInfo> productMap = new HashMap<Integer, ProductInfo>();
	
//	@PostConstruct
//	public void createInventory() {
//		productMap.put(1, new Product(1, "Laptop", "It's a lenovo laptop",1500));
//		productMap.put(2, new Product(2, "Mobile", "It's an apple mobile",500.65));
//		productMap.put(3, new Product(3, "Monitor", "It's a Dell monitor",250.50));
//		productMap.put(4, new Product(4, "Chair", "It's a wooden chair",300));
//		productMap.put(5, new Product(5, "Table", "It's a plastic table",80.35));
//		productMap.put(6, new Product(6, "Gaming Console", "It's an x-box console",2100.75));
//	}
	
	@GetMapping("/all")
	public List<ProductInfo> getAll(){
		List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
//		for(Entry<Integer,ProductInfo> e : productMap.entrySet()) {
//			products.add((ProductInfo)e.getValue());
//		}
		List<Product> products = productRepository.findAll();
		
		for(Product product : products) {
			ImageModel img = imageRepository.findById(product.getPicId()).get();
			//ImageModel img = product.getImageModel();
			ProductInfo productInfo = new ProductInfo(img,product);
			productInfos.add(productInfo);
		}
		
		return productInfos;
	}
	
//	@GetMapping("/get/{id}")
//	public ProductInfo getProduct(@RequestParam int id) {
//		return productMap.get(id);
//	}
	
//	@PostMapping("/add")
//	public void addProduct(@RequestBody Product product) throws ProductExecption{
//		if(productMap.containsKey(product.getId()))
//			throw new ProductExecption("Product already exist");
//		else
//			productMap.put(product.getId(), product);
//	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST, consumes = "multipart/form-data")
	public void  addProduct(@RequestPart("imageFile") MultipartFile file, @RequestPart("product") Product product) throws ProductExecption, IOException{
//		if(productMap.containsKey(product.getId()))
//			throw new ProductExecption("Product already exist");
//		else
//			productMap.put(product.getId(), product);
		System.out.println("Original Image Byte Size - " + file.getBytes().length + " image name: " + file.getOriginalFilename());
		
		ImageModel img = imageRepository.findByName(file.getOriginalFilename()).get();
		//ImageModel img = null;
			
		if(img == null) {
			img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
					file.getBytes());
			imageRepository.save(img);
		}
		
		Product prd = new Product(product.getId(),product.getName(),product.getDescription(),product.getPrice(),img.getId());
		//prd.setImageModel(img);
		//img.setProduct(prd);
		productRepository.save(prd);
		System.out.println("Product: " +prd);
		//ProductInfo prdInfo = new ProductInfo(img,prd);
		//productMap.put(product.getId(), prdInfo);
		//return img;
	}
	
//	@PutMapping("/edit")
//	public void editProduct(@RequestBody ProductInfo product) {
//		productMap.put(product.getId(), product);
//	}
	
//	@DeleteMapping("/delete/{id}")
//	public void deleteProduct(@RequestParam int id) {
//		productMap.remove(id);
//	}
}
