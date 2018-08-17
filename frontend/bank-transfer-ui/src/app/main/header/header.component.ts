import {Component, OnInit} from '@angular/core';
import {User} from "../../core/user/user.model";
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../core/user/user.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  user: User;

  constructor(private route: ActivatedRoute, private userService: UserService) {
  }

  ngOnInit() {
    this.getUser();
  }

  getUser(): void {
    const id = 2;
    this.userService.getUser(id)
      .subscribe(user => this.user = user);
  }

}
