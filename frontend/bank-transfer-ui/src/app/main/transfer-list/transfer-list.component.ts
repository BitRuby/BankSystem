import {Component, OnInit} from '@angular/core';
import {Transfer} from '../../core/transfer/transfer.model';
import {TransferFormModel} from '../../core/transfer/transfer.form.model';
import {ActivatedRoute} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TransferService} from '../../core/transfer/transfer.service';
import {TransferListModalComponent} from '../transfer-list-modal/transfer-list-modal.component';
import {TransferDetailsComponent} from '../transfer-details/transfer-details.component';


@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  transfer: Transfer[];
  transferProp: TransferFormModel;

  constructor(private route: ActivatedRoute, private modalService: NgbModal, private transferService: TransferService) {
    this.transferProp = {};
    this.transferProp.order = '&sort=createTime,desc';
    this.transferProp.batch = 10;
  }

  ngOnInit() {
    this.getTransfers();
  }

  private getTransfers(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.transferService.getTransfers(id, this.transferProp.batch, this.transferProp.order)
      .subscribe(transfer => {
        this.transfer = transfer['content'];
      });
  }

  private onScroll() {
    const number = 10;
    this.transferProp.batch += number;
    this.getTransfers();
  }

  private open() {
    const modalRef = this.modalService.open(TransferListModalComponent);
    modalRef.result.then((result) => {
      this.close(result);
    }, () => {
    });
  }

  private showDetails(id) {
    const modalRef = this.modalService.open(TransferDetailsComponent);
    modalRef.componentInstance.loadData(id);
    modalRef.result.then((result) => {
      this.close(result);
    }, () => {
    });
  }

  private close(modalData) {
    if (!modalData) {
      return;
    }
    if (modalData === 'pdf') {
      return;
    }
    this.transferProp.order = '';
    if (modalData.dateCheckbox) {
      this.transferProp.order += `&sort=createTime,${modalData.dateSelect || 'desc'}`;
    }
    if (modalData.titleCheckbox) {
      this.transferProp.order += `&sort=title,${modalData.titleSelect}`;
    }
    if (modalData.valueCheckbox) {
      this.transferProp.order += `&sort=value,${modalData.valueSelect}`;
    }
    this.getTransfers();
  }

}
