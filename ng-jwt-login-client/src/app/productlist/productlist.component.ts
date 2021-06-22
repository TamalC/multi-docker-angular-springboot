import { Productinfo } from './../productinfo';
import { Product } from './../product';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductremoteService } from '../productremote.service';
import { RegistrationService } from '../registration.service';
import { Router } from '@angular/router';
import { RetrievedProduct } from '../retrieved-product';

@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.css']
})
export class ProductlistComponent implements OnInit {
   
  retrievedImages: any[] = [];
  imageInfo : any;
  base64Data: any;
  image:any;
  

  retrievedProducts:RetrievedProduct[] = [];

  constructor(private _route: ActivatedRoute, private _service:ProductremoteService, private _regService : RegistrationService, private _router:Router) { }

  jwt:any;
  response:any;
  products:any[] = [];

  ngOnInit(): void {
    this.jwt = this._route.snapshot.paramMap.get('jwt');
    this.getAllProducts();
    
  }

  getAllProducts(){
    this._service.getProductList(this.jwt).subscribe(
      data => {
        this.retrievedProducts=data;
        this.retrieveImage();
        
      },
      error =>  {
        this.response=JSON.stringify(error);
        console.log(this.response);
        if(this.response.includes("Token has expired")){
            console.log("Refresh Token call is required");
            this.response = "Refresh Token call is required";
            console.log("token");
            this.getRefreshToken(this.jwt).subscribe(data=>{this.jwt=data;
              console.log(this.jwt);
              this.getAllProducts();
            });
          }
          if(this.response.includes("Inavlid token")){
            this.response = "Invalid Token";
            console.log("Inavlid token");
            this._router.navigate(['/errorpage',{errorMsg: this.response}]);
          }
      }
    )
  }

  public getRefreshToken(token:any){
    return this._regService.generateRefreshToken(token);
  }

  public submit(){
    this._router.navigate(['/addproducts', {jwt: this.jwt}]);
  }

  private retrieveImage(){
      for(let i = 0; i < this.retrievedProducts.length; i++){
        
        this.imageInfo = this.retrievedProducts[i].imageModel;
        this.base64Data = this.imageInfo.picByte;
        
        this.image = 'data:image/jpeg;base64,' + this.base64Data;
        
        
        this.retrievedProducts[i].imageModel = this.image;
        console.log(this.retrievedProducts[i].product);
        console.log(this.retrievedProducts[i].imageModel);
      }
      
  }

  logout(){
    this._regService.logout(this.jwt).subscribe(
      data => {
        this._router.navigate(['/']);
        
      },
      error =>  {
        this._router.navigate(['/']);
      }
    )
  }

}
