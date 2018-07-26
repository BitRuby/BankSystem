import {Component, OnInit} from '@angular/core';
import {TransferService} from '../../core/transfer/transfer.service';
import {Transfer} from '../../core/transfer/transfer.model';
import {ActivatedRoute} from '@angular/router';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgxSpinnerService} from 'ngx-spinner';

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  transfer: Transfer[];
  batch: number;
  closeResult: string;
  order: string;
  dateCheckbox: boolean;
  dateSelect: string;
  titleCheckbox: boolean;
  titleSelect: string;
  valueCheckbox: boolean;
  valueSelect: string;

  constructor(private transferService: TransferService, private route: ActivatedRoute,
              private modalService: NgbModal, private spinner: NgxSpinnerService) {
    this.dateSelect = 'asc';
    this.titleSelect = 'asc';
    this.valueSelect = 'asc';
    this.dateCheckbox = false;
    this.titleCheckbox = false;
    this.valueCheckbox = false;
    this.order = '&sort=createTime,desc';
    this.batch = 10;
  }

  ngOnInit() {
    this.getTransfers();
  }

  private getTransfers(): void {
    this.spinner.show();
    const id = +this.route.snapshot.paramMap.get('id');
    this.transferService.getTransfers(id, this.batch, this.order)
      .subscribe(transfer => {
        this.transfer = transfer['content'];
        setTimeout(() => {
          this.spinner.hide();
        }, 1000);
      });
  }

  private open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
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

  private onScroll() {
    this.batch += 10;
    this.getTransfers();
  }

  private close() {
    this.order = '';
    if (this.dateCheckbox) {
      this.order += '&sort=createTime,' + this.dateSelect;
    } else {
      this.order += '&sort=createTime,desc';
    }
    if (this.titleCheckbox) {
      this.order += '&sort=title,' + this.titleSelect;
    }
    if (this.valueCheckbox) {
      this.order += '&sort=value,' + this.valueSelect;
    }
    console.log(this.order);
    this.getTransfers();
  }

}
