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
  accountNo: string;

  constructor(private modalService: NgbModal) {
    this.currency = 'PLN';
    this.name = 'Przelew do kogoś';
    this.value = 24.99;
    this.accountNo = '25 0002 1010 0000 2020 9400 2067';
  }

  ngOnInit() {
  }

  private open(transfer) {
    this.modalService.open(transfer, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
}
