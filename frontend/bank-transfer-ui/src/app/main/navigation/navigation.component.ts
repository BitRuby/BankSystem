import {Component, OnInit} from '@angular/core';
import {User} from '../../core/user/user.model';
import {UserService} from '../../core/user/user.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  user: User;

  constructor(private route: ActivatedRoute, private userService: UserService) {

  }

  ngOnInit() {
    this.getUser();
  }

  getUser(): void {
    const id = +2;
    this.userService.getUser(id)
      .subscribe(user => this.user = user);
  }


}
