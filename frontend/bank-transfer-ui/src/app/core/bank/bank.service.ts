import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Bank} from './bank.model';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class BankService {
  constructor(private httpClient: HttpClient) {
  }

  getBankName(bank: string): Observable<Bank> {
    const url = `${environment.apiUrl}/banks/find-by-account-no/${bank}`;
    return this.httpClient.get<Bank>(url);
  }
}
