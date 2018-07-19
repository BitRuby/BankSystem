import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { MainComponent } from './main.component';
import { MainRoutingModule } from './main-routing.module';
import { DashboardComponent } from "./dashboard/dashboard.component";
import { AccountDetailsComponent } from "./account-details/account-details.component";
import { NewTransferComponent } from "./new-transfer/new-transfer.component";
import { TransferDetailsComponent } from "./transfer-details/transfer-details.component";
import { TransferListComponent } from "./transfer-list/transfer-list.component";
import { NavigationComponent } from "./navigation/navigation.component";
import { FooterComponent } from './footer/footer.component';

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
  ],
  imports: [
    CommonModule,
    MainRoutingModule
  ],
  providers: []
})
export class MainModule { }
