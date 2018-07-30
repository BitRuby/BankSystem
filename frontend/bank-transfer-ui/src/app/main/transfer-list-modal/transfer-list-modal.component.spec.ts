import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TransferListModalComponent} from './transfer-list-modal.component';

describe('TransferListModalComponent', () => {
  let component: TransferListModalComponent;
  let fixture: ComponentFixture<TransferListModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TransferListModalComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferListModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
