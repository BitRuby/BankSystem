import {Component, OnInit} from '@angular/core';
import {User} from '../../core/user/user.model';
import {UserService} from '../../core/user/user.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  user: User;

  constructor(private route: ActivatedRoute, private userService: UserService) {

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
