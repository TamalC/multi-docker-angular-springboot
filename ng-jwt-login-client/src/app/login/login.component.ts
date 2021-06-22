import { User } from './../user';
import { RegistrationService } from './../registration.service';
import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  user = new User();
  jwt:any;
  errorMsg:any;
  response:any;
  constructor(private _service : RegistrationService, private _router : Router) { }

  ngOnInit(): void {
    
  }

  loginUser(){
    this._service.loginUserFromRemote(this.user).subscribe(
      data => {this.jwt=data;
        console.log(this.jwt);
        //this._router.navigate(['/home']);
        if(this.user.username == "admin")
          this.accessWelcomeAdminApi();
        else
          this.accessWelcomeApi();
      },
      error => {console.log("Exception Occurred");
        this.errorMsg = "Bad Credentials. Please use the correct one";
      }
    )
  }

  accessWelcomeApi(){
    this._service.welcome(this.jwt).subscribe(
      data => {this.response=data;
        console.log(this.response);
        this._router.navigate(['/products', {jwt: this.jwt}]);
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
              this.accessWelcomeApi();
            });
          }
      }
    )
  }

  accessWelcomeAdminApi(){
    this._service.welcomeAdmin(this.jwt).subscribe(
      data => {this.response=data;
        console.log(this.response);
        //this._router.navigate(['/home', this.response]);
        this._router.navigate(['/products', {jwt: this.jwt}]);
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
              this.accessWelcomeAdminApi();
              //this._router.navigate(['/home', this.user.username]);
            });
          }
      }
    )
  }

  public getRefreshToken(token:any){
    return this._service.generateRefreshToken(token);
    //.subscribe(data => this.accessAdminApi(data));
  }

  goToRegistration(){
    this._router.navigate(['/registration']);
  }

}
