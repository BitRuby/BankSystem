import {Component, OnInit} from '@angular/core';
import {NewTransferModalComponent} from '../new-transfer-modal/new-transfer-modal.component';
import {TransferFormModel} from '../../core/transfer/transfer.form.model';

@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.css']
})
export class NewTransferComponent implements OnInit {
  transfer: TransferFormModel;

  constructor(private modalService: NgbModal) {
    this.transfer = {};
    this.transfer.currency = 'PLN';
  }

  ngOnInit() {
  }

  private open() {
    const modalRef = this.modalService.open(NewTransferModalComponent);
    modalRef.componentInstance.newTransferForm.name = this.transfer.name;
    modalRef.componentInstance.newTransferForm.currency = this.transfer.currency;
    modalRef.componentInstance.newTransferForm.value = this.transfer.value;
    modalRef.componentInstance.newTransferForm.accountNo = this.transfer.accountNo;
    modalRef.result.then((result) => {
    }, () => {
    });
  }

}
