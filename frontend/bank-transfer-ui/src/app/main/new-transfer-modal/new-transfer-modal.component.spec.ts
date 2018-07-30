import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {NewTransferModalComponent} from './new-transfer-modal.component';

describe('NewTransferModalComponent', () => {
  let component: NewTransferModalComponent;
  let fixture: ComponentFixture<NewTransferModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NewTransferModalComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewTransferModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
