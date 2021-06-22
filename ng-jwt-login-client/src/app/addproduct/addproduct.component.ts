import { Productinfo } from './../productinfo';
import { ProductremoteService } from './../productremote.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { RegistrationService } from '../registration.service';
import { ActivatedRoute, Router } from '@angular/router';
import {NgForm} from '@angular/forms'

@Component({
  selector: 'app-addproduct',
  templateUrl: './addproduct.component.html',
  styleUrls: ['./addproduct.component.css']
})
export class AddproductComponent implements OnInit {

  product:Product = new Product();    
  uploadMultiPartData = new FormData();
  
  constructor(private _route: ActivatedRoute, private _http:HttpClient, private _service:ProductremoteService, 
    private _regService : RegistrationService, private _router:Router) {
      // this.product = new Product();
      // this.uploadMultiPartData = new FormData();
     }
  jwt:any;
  response:any;
  selectedFile: any;
 
  resultProductInfo:any;
  retrievedImage:any;
  retrievedInfo:any;
  base64Data:any;
  
  
  ngOnInit(): void {
    this.jwt = this._route.snapshot.paramMap.get('jwt');
  }

  public onFileChanged(event:any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    console.log(this.selectedFile);
    console.log(JSON.stringify(this.product));
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    this.uploadMultiPartData.append('imageFile', this.selectedFile, this.selectedFile.name);
   

    
  }

  onAdd(){
    this.uploadMultiPartData.append('product', new Blob([JSON.stringify(this.product)],
    {
        type: "application/json"
    }));
    this.addProduct();
  }

  addProduct(){
    this._service.addProduct(this.jwt, this.uploadMultiPartData).subscribe(
      data => {
        this._router.navigate(['/products', {jwt: this.jwt}]);
      },
      error =>  {
        this.response=JSON.stringify(error);
        console.log(this.response);
        if(this.response.includes("Token has expired")){
            console.log("Refresh Token call is required");
            console.log("token");
            this.getRefreshToken(this.jwt).subscribe(data=>{this.jwt=data;
              console.log(this.jwt);
              this.addProduct();
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
