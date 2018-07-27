import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Component, OnInit} from '@angular/core';

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
