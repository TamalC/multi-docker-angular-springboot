import { User } from './user';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private _http : HttpClient) { }

  public loginUserFromRemote(user:User):Observable<any>{
    return this._http.post("http://localhost:8080/authenticate", user, {responseType: 'text' as 'json'});
  }

  public generateRefreshToken(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr).set("isRefreshToken","true");
    //let headers = new HttpHeaders().set("Authorization", tokenStr);
    //headers = headers.set("isRefreshToken","true");
    // const headers = new HttpHeaders({
    //   'Authorization': tokenStr,
    //   'isRefreshToken': 'true',
    // });
    console.log(headers);
    return this._http.get("http://localhost:8080/refreshToken", {headers, responseType: 'text' as 'json'});
  }

  public welcome(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    console.log(headers);
    return this._http.get("http://localhost:8080/", {headers, responseType: 'text' as 'json'});
  }

  public welcomeAdmin(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    return this._http.get("http://localhost:8080/admin", {headers, responseType: 'text' as 'json'});
  }

  public logout(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    console.log(headers);
    return this._http.get("http://localhost:8080/logbackendout", {headers, responseType: 'text' as 'json'});
  }
}
