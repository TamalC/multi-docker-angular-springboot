import { Productinfo } from './productinfo';
import { Product } from './product';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { RetrievedProduct } from './retrieved-product';

@Injectable({
  providedIn: 'root'
})
export class ProductremoteService {

  constructor(private _http:HttpClient) { }

  public getProductList(token:any):Observable<RetrievedProduct[]>{
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    console.log(headers);
    var options =  {
      headers: headers
  };
    return this._http.get<RetrievedProduct[]>("http://localhost:8080/product/all", options);
  }

  public addProduct(token:any, uploadMultiPartData:FormData):Observable<Productinfo>{
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    console.log(headers);
    var options =  {
      headers: headers
  };
    return this._http.post<Productinfo>("http://localhost:8080/product/add", uploadMultiPartData, options);
  }
}
