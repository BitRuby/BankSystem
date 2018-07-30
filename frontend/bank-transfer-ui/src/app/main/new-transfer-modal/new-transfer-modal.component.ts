import {Component, OnInit} from '@angular/core';
import {TransferFormModel} from "../../core/transfer/transfer.form.model";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-new-transfer-modal',
  templateUrl: './new-transfer-modal.component.html',
  styleUrls: ['./new-transfer-modal.component.css']
})
export class NewTransferModalComponent implements OnInit {

  newTransferForm: TransferFormModel;

  constructor(public activeModal: NgbActiveModal) {
    this.newTransferForm = {};
  }

  ngOnInit() {
  }

}
