import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AppApiConfigService {

  constructor(private http: HttpClient) {
    console.log("Create Api Config");
  }

  public getData() {
    
  }

}
