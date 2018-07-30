import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Component, OnInit} from '@angular/core';
import {NewTransferModalComponent} from "../new-transfer-modal/new-transfer-modal.component";

@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.css']
})
export class NewTransferComponent implements OnInit {
  name: string;
  currency: string;
  value: number;
  accountNo: number;

  constructor(private modalService: NgbModal) {
    this.currency = 'PLN';
  }

  ngOnInit() {
  }

  private open() {
    const modalRef = this.modalService.open(NewTransferModalComponent);
    modalRef.componentInstance.name = this.name;
    modalRef.componentInstance.currency = this.currency;
    modalRef.componentInstance.value = this.value;
    modalRef.componentInstance.accountNo = this.accountNo;
    modalRef.result.then((result) => {
      console.log(result);
    })
  }

}
