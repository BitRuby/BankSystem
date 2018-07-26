import {NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';
import {MainComponent} from './main.component';
import {MainRoutingModule} from './main-routing.module';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AccountDetailsComponent} from './account-details/account-details.component';
import {NewTransferComponent} from './new-transfer/new-transfer.component';
import {TransferDetailsComponent} from './transfer-details/transfer-details.component';
import {TransferListComponent} from './transfer-list/transfer-list.component';
import {NavigationComponent} from './navigation/navigation.component';
import {FooterComponent} from './footer/footer.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import localePl from '@angular/common/locales/pl';
import {AppGroupByPipe} from '../app-groupBy.pipe';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {NgxSpinnerModule} from 'ngx-spinner';
import {FormsModule} from '@angular/forms';


registerLocaleData(localePl);
@NgModule({
  declarations: [
    MainComponent,
    AccountDetailsComponent,
    DashboardComponent,
    NewTransferComponent,
    TransferDetailsComponent,
    TransferListComponent,
    FooterComponent,
    NavigationComponent,
    AppGroupByPipe
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    NgbModule,
    InfiniteScrollModule,
    NgxSpinnerModule,
    FormsModule
  ],
  providers: []
})
export class MainModule { }
