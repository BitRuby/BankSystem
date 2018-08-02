import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {Transfer} from './transfer.model';
import {environment} from '../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class TransferService {
  constructor(private httpClient: HttpClient) {
  }

  getTransfers(id: number, batch: number, order: string): Observable<Transfer[]> {
    const url = `${environment.apiUrl}/transfers/user/${id}?size=${batch}${order}`;
    return this.httpClient.get<Transfer[]>(url);
  }
}
