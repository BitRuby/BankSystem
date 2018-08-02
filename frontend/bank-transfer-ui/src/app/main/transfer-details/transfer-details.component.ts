import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TransferContent} from '../../core/transfer/transfer.content.model';
import {TransferService} from '../../core/transfer/transfer.service';
import {DocsService} from '../../core/docs/docs.service';

@Component({
  selector: 'app-transfer-details',
  templateUrl: './transfer-details.component.html',
  styleUrls: ['./transfer-details.component.css']
})
export class TransferDetailsComponent implements OnInit {
  transferContent: TransferContent;

  constructor(public activeModal: NgbActiveModal, private transferService: TransferService, private docsService: DocsService) {
    this.transferContent = {} as TransferContent;
  }
  ngOnInit() {
  }

  loadData(id) {
    this.transferService.getOneTransfer(id)
      .subscribe(transfer => {
        this.transferContent = transfer;
      });
  }

  private pdf() {
    return this.docsService.getPdf(this.transferContent.id);

  }
}
