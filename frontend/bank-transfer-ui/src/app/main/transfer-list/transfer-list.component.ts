import {Component, OnInit} from '@angular/core';
import {Transfer} from '../../core/transfer/transfer.model';
import {TransferService} from '../../core/transfer/transfer.service';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute} from '@angular/router';
import {NgxSpinnerService} from 'ngx-spinner';
import {TransferListModalComponent} from "../transfer-list-modal/transfer-list-modal.component";

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  transfer: Transfer[];
  batch: number;
  order: string;

  constructor(private route: ActivatedRoute, private spinner: NgxSpinnerService,
              private transferService: TransferService, private modalService: NgbModal) {
    this.order = '&sort=createTime,desc';
    this.batch = 10;
  }

  ngOnInit() {
    this.getTransfers();
  }

  private getTransfers(): void {
    /*this.spinner.show();*/
    const id = +this.route.snapshot.paramMap.get('id');
    this.transferService.getTransfers(id, this.batch, this.order)
      .subscribe(transfer => {
        this.transfer = transfer['content'];
        /*setTimeout(() => {
          this.spinner.hide();
        }, 1000);*/
      });
  }

  private onScroll() {
    const number = 10;
    this.batch += number;
    this.getTransfers();
  }

  private open() {
    const modalRef = this.modalService.open(TransferListModalComponent);
    modalRef.result.then((result) => {
      this.close(result);
    })
  }

  private close(modalData) {
    this.order = '';
    if (modalData.dateCheckbox) {
      this.order += `&sort=createTime,${modalData.dateSelect || 'desc'}`;
    }
    if (modalData.titleCheckbox) {
      this.order += `&sort=title,${modalData.titleSelect}`;
    }
    if (modalData.valueCheckbox) {
      this.order += `&sort=value,${modalData.valueSelect}`;
    }
    this.getTransfers();
  }

}
