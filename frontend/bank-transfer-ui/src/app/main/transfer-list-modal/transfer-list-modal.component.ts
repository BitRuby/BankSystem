import {Component, OnInit} from '@angular/core';
import {TransferFormModel} from "../../core/transfer/transfer.form.model";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-transfer-list-modal',
  templateUrl: './transfer-list-modal.component.html',
  styleUrls: ['./transfer-list-modal.component.css']
})
export class TransferListModalComponent implements OnInit {

  transferForm: TransferFormModel;

  constructor(public activeModal: NgbActiveModal) {
    this.transferForm = {};
    this.transferForm.dateSelect = 'asc';
    this.transferForm.titleSelect = 'asc';
    this.transferForm.valueSelect = 'asc';
    this.transferForm.dateCheckbox = false;
    this.transferForm.titleCheckbox = false;
    this.transferForm.valueCheckbox = false;
  }

  ngOnInit() {
  }

}
