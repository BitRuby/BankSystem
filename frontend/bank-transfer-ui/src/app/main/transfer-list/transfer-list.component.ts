import {Component, Input, OnInit} from '@angular/core';
import {TransferService} from '../../core/transfer/transfer.service';
import {Transfer} from '../../core/transfer/transfer.model';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  @Input() transfer: Transfer[];

  constructor(private transferService: TransferService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getTransfers();
  }

  getTransfers(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.transferService.getTransfers(id)
      .subscribe(transfer => this.transfer = transfer);
  }

}
