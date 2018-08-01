import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Component, OnInit} from '@angular/core';
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.css']
})
export class NewTransferComponent implements OnInit {
  closeResult: string;
  name: string;
  currency: string;
  value: number;
  accountNo: number;

  constructor(private modalService: NgbModal) {
    this.currency = 'PLN';
  }

  ngOnInit() {
  }

  open() {
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
