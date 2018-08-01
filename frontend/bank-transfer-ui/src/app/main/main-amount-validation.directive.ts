import {Directive, Input} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from '@angular/forms';


@Directive({
  selector: '[appValidateAccount]',
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: MainAmountValidationDirective,
    multi: true
  }]
})
export class MainAmountValidationDirective implements Validator {
  @Input() appValidateAccount: string;

  validate(control: AbstractControl): { [key: string]: any } | null {
    if (!control.value) {
      return null;
    }
    const regex = new RegExp('^[1-9][0-9]*([.][0-9]{2}|)$');
    if (!regex.test(control.value)) {
      return {'format': true};
    } else {
      return null;
    }
  }

}
