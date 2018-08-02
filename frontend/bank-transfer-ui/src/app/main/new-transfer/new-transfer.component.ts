import {Component, OnInit, ViewChild} from '@angular/core';
import {TransferFormModel} from '../../core/transfer/transfer.form.model';
import {TransferAlert} from '../../core/transfer/transfer.alert.model';
import {NgForm} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {BankService} from '../../core/bank/bank.service';
import {TransferService} from '../../core/transfer/transfer.service';
import {NewTransferModalComponent} from '../new-transfer-modal/new-transfer-modal.component';


@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.css']
})
export class NewTransferComponent implements OnInit {
  transfer: TransferFormModel;
  transferAlert: TransferAlert;
  @ViewChild('newTransfer') public newTransfer: NgForm;

  constructor(private modalService: NgbModal, private bankService: BankService, private transferService: TransferService) {
    this.transfer = {};
    this.transferAlert = {};
    this.transfer.currency = 'PLN';
  }

  ngOnInit() {
  }

  private getBankName() {
    this.bankService.getBankName(this.transfer.accountNo)
      .subscribe(transfer => {
        this.transfer.bankName = transfer.bank;
      }, error => {
        this.transfer.bankName = '';
      });
  }

  private put() {
    this.transferService.sendTransfers({
      'title': this.transfer.name,
      'value': this.transfer.value,
      'accountId': {
        'id': 2
      },
      'accountNo': this.transfer.accountNo,
      'currency': this.transfer.currency,
      'transferType': 'OUTGOING'
    })
      .subscribe(transfer => {
        this.transferAlert.transferSuccess = true;
        this.transferAlert.transferRejected = false;
      }, error => {
        this.transferAlert.transferSuccess = false;
        this.transferAlert.transferRejected = true;
        this.transferAlert.rejectMessage = error.error.error;
      });
  }

  private clear() {
    this.newTransfer.reset();
    this.transfer = {};
    this.transfer.currency = 'PLN';
  }

  private open() {
    const modalRef = this.modalService.open(NewTransferModalComponent);
    modalRef.componentInstance.newTransferForm.name = this.transfer.name;
    modalRef.componentInstance.newTransferForm.currency = this.transfer.currency;
    modalRef.componentInstance.newTransferForm.value = this.transfer.value;
    modalRef.componentInstance.newTransferForm.accountNo = this.transfer.accountNo;
    modalRef.componentInstance.newTransferForm.bankName = this.transfer.bankName;
    modalRef.result.then((result) => {
      this.put();
      this.clear();
    }, () => {
    });
  }

}
