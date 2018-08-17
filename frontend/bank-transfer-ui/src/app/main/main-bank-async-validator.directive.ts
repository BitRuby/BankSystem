import {Directive, Input} from '@angular/core';
import {AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS} from '@angular/forms';
import {BankService} from '../core/bank/bank.service';
import {Observable, of} from 'rxjs/index';
import {ValidationErrors} from '@angular/forms/src/directives/validators';
import {catchError, map} from 'rxjs/operators';


@Directive({
  selector: '[appAsyncBankValidator]',
  providers: [{
    provide: NG_ASYNC_VALIDATORS,
    useExisting: MainBankAsyncValidatorDirective,
    multi: true
  }]
})
export class MainBankAsyncValidatorDirective implements AsyncValidator {
  @Input() appAsyncBankValidator: string;

  constructor(private bankService: BankService) {
  }

  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    return control.value ? this.bankService.getBankName(control.value)
      .pipe(map((response) => {
          return null;
        }), catchError((error) => of<ValidationErrors>({'bank': true}))
      ) : of<null>();
  }

}
