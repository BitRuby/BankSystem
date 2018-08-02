import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../../environments/environment';
import {TransferContent} from './transfer.content.model';
import {Transfer} from './transfer.model';
import {TransferRequest} from './transfer.request.model';


@Injectable({
  providedIn: 'root',
})
export class TransferService {
  constructor(private httpClient: HttpClient) {
  }

  getOneTransfer(id: number): Observable<TransferContent> {
    const url = `${environment.apiUrl}/transfers/${id}`;
    return this.httpClient.get<TransferContent>(url);
  }

  getTransfers(id: number, batch: number, order: string): Observable<Transfer[]> {
    const url = `${environment.apiUrl}/transfers/user/${id}?size=${batch}${order}`;
    return this.httpClient.get<Transfer[]>(url);
  }

  sendTransfers(body: TransferRequest): Observable<TransferRequest> {
    const url = `${environment.apiUrl}/transfers`;
    return this.httpClient.post<TransferRequest>(url, body);
  }
}
