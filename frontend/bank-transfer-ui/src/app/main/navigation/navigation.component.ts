import { Component, OnInit } from '@angular/core';
import  $ from 'jquery';
@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor() {
    $(window).on("load resize scroll",function(e){
      var upHeight = $('.navbar').height() + $('.header').height();
      $('.content').css("min-height", "calc(100vh - 90px - "+upHeight+"px)");
    });
  }

  ngOnInit() {
  }

}
