import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private _route: ActivatedRoute) { }
  response:any;
  ngOnInit(): void {
    this.response = this._route.snapshot.paramMap.get('response');
  }

}
