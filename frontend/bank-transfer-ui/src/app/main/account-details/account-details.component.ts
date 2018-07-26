import {Component, Input, OnInit} from '@angular/core';
import { UserService } from "../../core/user/user.service";
import { User } from "../../core/user/user.model";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  @Input() user: User;
  constructor(private userService: UserService, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.getUser();
  }

  getUser(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.userService.getUser(id)
      .subscribe(user => this.user = user);
  }

}
