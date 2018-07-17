import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailsComponent } from "./account-details/account-details.component";
import { NewTransferComponent } from "./new-transfer/new-transfer.component";
import { TransferListComponent } from "./transfer-list/transfer-list.component";
import { DashboardComponent } from "./dashboard/dashboard.component";

const routes: Routes = [
  { path: 'account-details', component: AccountDetailsComponent },
  { path: 'new-transfer', component: NewTransferComponent },
  { path: 'transfer-list', component: TransferListComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})


export class MainRoutingModule { }
