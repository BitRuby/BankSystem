import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AccountDetailsComponent} from "./account-details/account-details.component";
import {NewTransferComponent} from "./new-transfer/new-transfer.component";
import {TransferListComponent} from "./transfer-list/transfer-list.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {TransferDetailsComponent} from "./transfer-details/transfer-details.component";
import {MainComponent} from "./main.component";

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {
    path: '', component: MainComponent,
    children: [
      { path: 'account-details/:id', component: AccountDetailsComponent },
      { path: 'transfer-details', component: TransferDetailsComponent },
      { path: 'new-transfer', component: NewTransferComponent },
      {path: 'transfer-list/:id', component: TransferListComponent},
      { path: 'dashboard', component: DashboardComponent },
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})


export class MainRoutingModule { }
