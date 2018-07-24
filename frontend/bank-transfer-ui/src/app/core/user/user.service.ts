import {Injectable} from '@angular/core';
import {User} from './user.model';
import {Observable} from 'rxjs/internal/Observable';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private httpClient: HttpClient) {}

  getUser(id: number): Observable<User> {
    const url = `${environment.apiUrl}/accounts/${id}`;
    return this.httpClient.get<User>(url);
  }
}
