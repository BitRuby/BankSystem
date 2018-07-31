import {NgModule} from '@angular/core';
import {MainComponent} from './main.component';
import {AccountDetailsComponent} from './account-details/account-details.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {NewTransferComponent} from './new-transfer/new-transfer.component';
import {NewTransferModalComponent} from './new-transfer-modal/new-transfer-modal.component';
import {TransferDetailsComponent} from './transfer-details/transfer-details.component';
import {TransferListComponent} from './transfer-list/transfer-list.component';
import {TransferListModalComponent} from './transfer-list-modal/transfer-list-modal.component';
import {NavigationComponent} from './navigation/navigation.component';
import {AppGroupByPipe} from '../app-groupBy.pipe';
import {CommonModule, registerLocaleData} from '@angular/common';
import {MainRoutingModule} from './main-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {FormsModule} from '@angular/forms';
import localePl from '@angular/common/locales/pl';
import {HeaderComponent} from './header/header.component';

registerLocaleData(localePl);
@NgModule({
  declarations: [
    MainComponent,
    AccountDetailsComponent,
    DashboardComponent,
    NewTransferComponent,
    NewTransferModalComponent,
    TransferDetailsComponent,
    TransferListComponent,
    TransferListModalComponent,
    NavigationComponent,
    AppGroupByPipe,
    HeaderComponent
  ],
  entryComponents: [
    TransferListModalComponent,
    NewTransferModalComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    NgbModule,
    InfiniteScrollModule,
    FormsModule
  ]
})
export class MainModule { }
