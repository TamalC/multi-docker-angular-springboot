import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent implements OnInit {

  errorMsg:any;
  constructor(private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this.errorMsg=this._route.snapshot.paramMap.get('errorMsg');
  }

}
