import { ErrorPageComponent } from './error-page/error-page.component';
import { ProductlistComponent } from './productlist/productlist.component';
import { RegistrationComponent } from './registration/registration.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddproductComponent } from './addproduct/addproduct.component';

const routes: Routes = [
  {path:'',component:LoginComponent},
  {path:'home/:response',component:HomeComponent},
  {path:'registration',component:RegistrationComponent},
  {path:'products',component:ProductlistComponent},
  {path:'addproducts',component:AddproductComponent},
  {path:'errorpage',component:ErrorPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
