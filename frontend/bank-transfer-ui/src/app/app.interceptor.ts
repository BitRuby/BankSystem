import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {finalize, tap} from 'rxjs/operators';
import {NgxSpinnerService} from 'ngx-spinner';


@Injectable()
export class AppInterceptor implements HttpInterceptor {
  constructor(private spinner: NgxSpinnerService) {
  }
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // this.spinner.show();
    return next.handle(req)
      .pipe(
        tap(event => {
          if (event instanceof HttpResponse) {
            console.log('all looks good');
            console.log(event.status);
          }
        }, error => {
          console.log('----response----');
          console.error('status code:');
          console.error(error.status);
          console.error(error.message);
          console.log('--- end of response---');
        }),
        finalize(() => {
          setTimeout(() => {
            // this.spinner.hide();
          }, 1000);
        })
      );

  }
}
