import {Component, OnInit} from '@angular/core';
import {TransferFormModel} from '../../core/transfer/transfer.form.model';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {BankService} from '../../core/bank/bank.service';
import {NewTransferModalComponent} from '../new-transfer-modal/new-transfer-modal.component';

@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.css']
})
export class NewTransferComponent implements OnInit {
  transfer: TransferFormModel;


  constructor(private modalService: NgbModal, private bankService: BankService) {

    this.transfer = {};
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
  private onSubmit() {
    console.log('Valid');
  }
  private open() {
    const modalRef = this.modalService.open(NewTransferModalComponent);
    modalRef.componentInstance.newTransferForm.name = this.transfer.name;
    modalRef.componentInstance.newTransferForm.currency = this.transfer.currency;
    modalRef.componentInstance.newTransferForm.value = this.transfer.value;
    modalRef.componentInstance.newTransferForm.accountNo = this.transfer.accountNo;
    modalRef.componentInstance.newTransferForm.bankName = this.transfer.bankName;
    modalRef.result.then((result) => {
    }, () => {
    });
  }

}
