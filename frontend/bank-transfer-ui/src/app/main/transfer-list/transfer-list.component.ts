import {Component, Input, OnInit} from '@angular/core';
import {TransferService} from '../../core/transfer/transfer.service';
import {Transfer} from '../../core/transfer/transfer.model';
import {ActivatedRoute} from '@angular/router';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  @Input() transfer: Transfer[];
  batch: number;
  closeResult: string;
  spinner: boolean;
  constructor(private transferService: TransferService, private route: ActivatedRoute, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.batch = 10;
    this.spinner = true;
    this.getTransfers();
  }

  private getTransfers(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.transferService.getTransfers(id, this.batch).pipe(finalize(() => {
      this.spinner = true;
    }))
      .subscribe(transfer => this.transfer = transfer['content']);
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
    console.log('Scroll!');
    this.batch += 10;
    this.getTransfers();
  }

}
