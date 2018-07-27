import {NgModule} from '@angular/core';
import {LoginComponent} from './login.component';
import {FooterComponent} from './footer/footer.component';
import {CommonModule} from '@angular/common';
import {LoginRoutingModule} from './login-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    LoginComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    LoginRoutingModule,
    NgbModule
  ]
})
export class LoginModule { }
