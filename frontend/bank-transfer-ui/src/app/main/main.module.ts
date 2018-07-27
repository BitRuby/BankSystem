import {NgModule} from '@angular/core';
import {MainComponent} from './main.component';
import {AccountDetailsComponent} from './account-details/account-details.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {NewTransferComponent} from './new-transfer/new-transfer.component';
import {TransferDetailsComponent} from './transfer-details/transfer-details.component';
import {TransferListComponent} from './transfer-list/transfer-list.component';
import {FooterComponent} from './footer/footer.component';
import {NavigationComponent} from './navigation/navigation.component';
import {AppGroupByPipe} from '../app-groupBy.pipe';
import {CommonModule, registerLocaleData} from '@angular/common';
import {MainRoutingModule} from './main-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {NgxSpinnerModule} from 'ngx-spinner';
import {FormsModule} from '@angular/forms';
import localePl from '@angular/common/locales/pl';


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
