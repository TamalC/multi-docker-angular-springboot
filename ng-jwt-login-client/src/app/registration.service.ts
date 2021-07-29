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
    return this._http.post("/api/authenticate", user, {responseType: 'text' as 'json'});
  }

  public generateRefreshToken(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr).set("isRefreshToken","true");
    console.log(headers);
    return this._http.get("/api/refreshToken", {headers, responseType: 'text' as 'json'});
  }

  public welcome(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    console.log(headers);
    return this._http.get("/api/", {headers, responseType: 'text' as 'json'});
  }

  public welcomeAdmin(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    return this._http.get("/api/admin", {headers, responseType: 'text' as 'json'});
  }

  public logout(token:any){
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    let headers = new HttpHeaders().set("Authorization", tokenStr);
    console.log(headers);
    return this._http.get("/api/logbackendout", {headers, responseType: 'text' as 'json'});
  }
}
