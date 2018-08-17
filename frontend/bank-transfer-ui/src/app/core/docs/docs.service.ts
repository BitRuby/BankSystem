import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class DocsService {
  constructor(private httpClient: HttpClient) {
  }

  getPdf(id: number): string {
    const url = `${environment.apiUrl}/pdf/download/${id}`;
    return url;
  }
}
